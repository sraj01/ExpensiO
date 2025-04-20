package com.example.myapplication.ui.theme

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


//TODO:Write all functions for auth in this repo
object AuthRepo {
    fun signUp(email: String,password: String): Task<AuthResult?> {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
    }
}