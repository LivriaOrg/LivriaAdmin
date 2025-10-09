package com.example.adminlivria.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.adminlivria.R
import com.example.adminlivria.common.navigation.NavDestinations
import com.example.adminlivria.common.ui.theme.AsapCondensedFontFamily
import com.example.adminlivria.common.ui.theme.LivriaAmber
import com.example.adminlivria.common.ui.theme.LivriaBlue
import com.example.adminlivria.common.ui.theme.LivriaLightGray
import com.example.adminlivria.common.ui.theme.LivriaNavyBlue
import com.example.adminlivria.common.ui.theme.LivriaOrange
import com.example.adminlivria.common.ui.theme.LivriaSoftCyan
import com.example.adminlivria.common.ui.theme.LivriaYellowLight

@Composable
fun LivriaTopBar(
    navController: NavController,
    currentRoute: String?,
    currentCapitalValue: Double
) {
    val isSettingsSelected = currentRoute == NavDestinations.SETTINGS_PROFILE_ROUTE
    val currentCapital = String.format("%.2f", currentCapitalValue)

    Column(
            modifier = Modifier.fillMaxWidth()
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
            ){
                // --- 1. SECCIÓN IZQUIERDA: LOGO LIVRIA ---
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Livria Logo",
                        modifier = Modifier.height(24.dp)
                    )
                }

                // --- 2. SECCIÓN DERECHA: CAPITAL Y SETTINGS ---
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Capital:",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = currentCapital,
                        color = LivriaOrange,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = AsapCondensedFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp
                        )
                    )

                    Spacer(Modifier.width(16.dp))

                    IconButton(
                        onClick = {
                            navController.navigate(NavDestinations.SETTINGS_PROFILE_ROUTE)
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.Transparent)
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

            // La línea de colores debajo de la barra superior
            Row(
                Modifier.fillMaxWidth().height(4.dp)
            ) {
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(LivriaOrange)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(LivriaAmber)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(LivriaYellowLight)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(LivriaNavyBlue)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(LivriaLightGray)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(LivriaSoftCyan)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(LivriaBlue)
                )
            }
        }
}