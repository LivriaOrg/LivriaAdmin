// LivriaTopBar.kt (reemplazar)
package com.example.adminlivria.common.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.adminlivria.R
import com.example.adminlivria.common.navigation.NavDestinations
import com.example.adminlivria.common.ui.theme.*
import com.example.adminlivria.profilecontext.data.local.TokenManager
import com.example.adminlivria.profilecontext.presentation.SettingsViewModel
import com.example.adminlivria.profilecontext.presentation.SettingsViewModelFactory
import com.example.adminlivria.profilecontext.data.remote.UserAdminService

@Composable
fun LivriaTopBar(
    navController: NavController,
    currentRoute: String?,
    userAdminService: UserAdminService,
    tokenManager: TokenManager
) {
    val TAG = "LivriaTopBar"

    // 1) Factory + ViewModel: UNA SOLA VEZ
    val factory = remember {
        SettingsViewModelFactory(
            userAdminService = userAdminService,
            tokenManager = tokenManager
        )
    }
    val settingsViewModel: SettingsViewModel = viewModel(factory = factory)
    val settingsState by settingsViewModel.uiState.collectAsState()

    // Debug: mostrar info cuando se monte TopBar y cuando llame al load
    LaunchedEffect(key1 = tokenManager.getToken()) {
        val token = tokenManager.getToken()
        Log.d("LivriaTopBar", "token changed in TopBar -> $token ; VM=${settingsViewModel.hashCode()}")
        if (token != null && token.isNotBlank()) {
            // si ya hay token, cargamos. Si token es null, no hace la petición.
            settingsViewModel.loadAdminData()
            Log.d("LivriaTopBar", "TopBar requested loadAdminData() on VM=${settingsViewModel.hashCode()}")
        }
    }

    // Debug: cada vez que cambie el capital, lo logueamos (ayuda a saber si actualiza)
    LaunchedEffect(settingsState.capital) {
        Log.d(TAG, "TopBar VM=${settingsViewModel.hashCode()} capital changed => ${settingsState.capital}")
    }

    val isSettingsSelected = currentRoute == NavDestinations.SETTINGS_PROFILE_ROUTE
    val currentCapital = String.format("%.2f", settingsState.capital)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Livria Logo",
                    modifier = Modifier.height(24.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Capital:",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp)
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = currentCapital,
                    color = LivriaOrange,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = AsapCondensedFontFamily,
                        fontSize = 24.sp
                    )
                )

                Spacer(Modifier.width(16.dp))

                IconButton(
                    onClick = { navController.navigate(NavDestinations.SETTINGS_PROFILE_ROUTE) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "Settings",
                        tint = if (isSettingsSelected) LivriaAmber else Color.White,
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
        }

        Row(Modifier.fillMaxWidth().height(4.dp)) {
            Box(Modifier.weight(1f).fillMaxHeight().background(LivriaOrange))
            Box(Modifier.weight(1f).fillMaxHeight().background(LivriaAmber))
            Box(Modifier.weight(1f).fillMaxHeight().background(LivriaYellowLight))
            Box(Modifier.weight(1f).fillMaxHeight().background(LivriaNavyBlue))
            Box(Modifier.weight(1f).fillMaxHeight().background(LivriaLightGray))
            Box(Modifier.weight(1f).fillMaxHeight().background(LivriaSoftCyan))
            Box(Modifier.weight(1f).fillMaxHeight().background(LivriaBlue))
        }
    }
}
