package com.example.myapplication

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.theme.AuthRepo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {

    public val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile

    init {
        val uid = Firebase.auth.currentUser?.uid
        if (uid != null) {
            Firebase.firestore
                .collection("users")
                .document(uid)
                .addSnapshotListener { doc, _ ->
                    _profile.value = doc?.toObject(UserProfile::class.java)
                }
        }
        // If uid is null, we simply do nothing—_profile stays null
    }

    //TODO: write all functions in the below format with the below flow
    /**
     *
     * ALSO RENAME this Class with AuthViewModel (for understanding)
     * Compose func will call viewmodel function
     * Viewmodel function will call repo
     * Repo will be a singleton class where all the functions will reside.
     * */
    fun signUp(password: String,confirmPassword: String,email: String,context: Context) : Boolean{
        if (password != confirmPassword) {
            return false
        }

        else if (email.isBlank() || password.isBlank()|| confirmPassword.isBlank())

        {
            Toast.makeText(context, "Email Or password cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }

        else {
            val result  = AuthRepo.signUp(email,password)
            return result.isSuccessful
        }
    }

}


data class UserProfile(
    val name: String = "",
    val profilePictureUrl: String = ""
)
