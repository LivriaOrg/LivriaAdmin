package com.example.adminlivria.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize())
    { paddingValues ->
        // Esto es un placeholder hasta que haya la pantalla Home
        Text(text = "Bienvenido a LIVRIA Admin", modifier = Modifier.fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(paddingValues))
    }

}