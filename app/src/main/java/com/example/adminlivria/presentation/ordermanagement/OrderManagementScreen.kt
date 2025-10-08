package com.example.adminlivria.presentation.ordermanagement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Scaffold

@Composable
fun OrdersScreen(
    navController: NavController,
    viewModel: OrderManagementViewModel = viewModel()
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues)
        ) {

        }

    }

}

@Composable
fun MiniStats() {
    
}