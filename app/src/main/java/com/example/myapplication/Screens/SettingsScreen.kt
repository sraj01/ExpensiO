package com.example.myapplication.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController){
    Box(modifier = Modifier.fillMaxWidth()
        .fillMaxWidth(),
        Alignment.Center
    )
    {
        Text("hello this is a settings screen")
    }
}