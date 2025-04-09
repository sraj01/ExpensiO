package com.example.myapplication.Screens
// ProfileSetupScreen.kt


import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupScreen(navController: NavController) {
    val context = LocalContext.current

    // State variables for user input and image selection
    var userName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Image picker launcher using ActivityResultContracts.GetContent
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture Section
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable { imagePickerLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (profileImageUri != null) {
                // Use Coil to load the selected image from the URI
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = "Selected Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Show a placeholder icon if no image is selected
                Image(
                    painter = painterResource(id = R.drawable.splash_logo),
                    contentDescription = "Profile Placeholder",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Input for Name
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Input for Phone Number
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White)
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Continue Button: uploads image if selected, then saves profile data
        Button(
            onClick = {
                // Validate inputs
                if (userName.isBlank() || phoneNumber.isBlank()) {
                    errorMessage = "All fields are required."
                    return@Button
                }
                errorMessage = ""

                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId == null) {
                    Toast.makeText(context, "User not authenticated.", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (profileImageUri != null) {
                    // Upload image to Firebase Storage
                    isUploading = true
                    val storageRef = FirebaseStorage.getInstance().reference
                    val profilePicRef = storageRef.child("profilePics/$userId.jpg")

                    profilePicRef.putFile(profileImageUri!!)
                        .addOnSuccessListener  { uri ->
                        isUploading = false
                        // Directly save the user profile with the download URL
                        saveUserProfile(userId, userName, phoneNumber, uri.toString(), navController, context)
                    }

                        .addOnFailureListener { exception ->
                                Toast.makeText(context, "Download URL error: ${exception.message}", Toast.LENGTH_SHORT).show()

                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(context, "Image upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }

                } else {
                    // No image selected; save profile data with an empty URL
                    saveUserProfile(
                        userId = userId,
                        userName = userName,
                        phoneNumber = phoneNumber,
                        profileUrl = "",
                        navController = navController,
                        context = context
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isUploading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
            } else {
                Text("Continue")
            }
        }
    }
}

// Helper function to save the user profile to Firestore
fun saveUserProfile(
    userId: String,
    userName: String,
    phoneNumber: String,
    profileUrl: String,
    navController: NavController,
    context: Context
) {
    val userProfile = hashMapOf(
        "name" to userName,
        "phone" to phoneNumber,
        "profilePictureUrl" to profileUrl
    )
    FirebaseFirestore.getInstance().collection("users")
        .document(userId)
        .set(userProfile)
        .addOnSuccessListener {
            Toast.makeText(context, "Profile saved successfully", Toast.LENGTH_SHORT).show()
            navController.navigate("home") {
                popUpTo("profileSetup") { inclusive = true }
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(context, "Failed to save profile: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
}
