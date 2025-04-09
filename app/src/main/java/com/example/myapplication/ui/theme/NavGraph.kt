package com.example.myapplication.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.Screens.AboutScreen
import com.example.myapplication.Screens.NewTransaction
import com.example.myapplication.Screens.HomeScreen
import com.example.myapplication.Screens.LoginScreen
import com.example.myapplication.Screens.NewTransaction
import com.example.myapplication.Screens.ProfileSetupScreen
import com.example.myapplication.Screens.SettingsScreen
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
        composable("newTransaction"){ NewTransaction(navController) }
    }
}

fun getStartDestination(): String {
    return if (FirebaseAuth.getInstance().currentUser != null) "home" else "login"
}


