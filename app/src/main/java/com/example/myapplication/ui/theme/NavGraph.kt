package com.example.myapplication.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.screens.AboutScreen
import com.example.myapplication.screens.AddTransactionScreen
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.LoginScreen
import com.example.myapplication.screens.ProfileSetupScreen
import com.example.myapplication.screens.SettingsScreen
import com.example.myapplication.ui.Screens.SignupScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = getStartDestination()) {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("profileSetup") { ProfileSetupScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("settings"){SettingsScreen(navController)}
        composable("about"){AboutScreen(navController)}
        composable("newTransaction"){ AddTransactionScreen (navController, onCancel = {navController.popBackStack()},
      onSave =   )}
    }
}

fun getStartDestination(): String {
    return if (FirebaseAuth.getInstance().currentUser != null) "home" else "login"
}


