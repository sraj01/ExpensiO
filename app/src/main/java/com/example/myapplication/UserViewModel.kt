package com.example.myapplication

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
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
}



data class UserProfile(
    val name: String = "",
    val profilePictureUrl: String = ""
)
