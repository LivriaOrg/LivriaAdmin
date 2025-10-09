package com.example.adminlivria.searchcontext.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.adminlivria.R
import com.example.adminlivria.common.navigation.NavDestinations
import com.example.adminlivria.profilecontext.presentation.WelcomeCard
import com.example.adminlivria.common.ui.theme.AlexandriaFontFamily
import com.example.adminlivria.common.ui.theme.LivriaBlack
import com.example.adminlivria.common.ui.theme.LivriaLightGray
import com.example.adminlivria.common.ui.theme.LivriaNavyBlue
import com.example.adminlivria.common.ui.theme.LivriaOrange
import com.example.adminlivria.common.ui.theme.LivriaSoftCyan
import com.example.adminlivria.common.ui.theme.LivriaWhite
import com.example.adminlivria.common.ui.theme.LivriaYellowLight

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {
    val state = viewModel.uiState

    Scaffold(
        modifier = Modifier.fillMaxSize())
    { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding(24.dp, 0.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LivriaWhite,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                // --- 1. SECCIÓN DE BIENVENIDA ---
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "Welcome to the Library Management System",
                        textAlign = TextAlign.Center,
                        color = LivriaOrange,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .padding(6.dp)
                    )
                    Text(
                        "Manage your book collection efficiently with our tools",
                        textAlign = TextAlign.Center,
                        color = LivriaBlack,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 3.dp)
                    )

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 18.dp),
                        thickness = 2.dp,
                        color = LivriaSoftCyan
                    )

                    WelcomeCard(state.user.username, state.user.fullName)
                }

                // --- 2. SECCIÓN DE QUICK ACTIONS ---
                Column {
                    Text(
                        "Quick Actions",
                        textAlign = TextAlign.Center,
                        color = LivriaNavyBlue,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 6.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionButton(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.ic_book),
                            label = "Manage Books",
                            onClick = { navController.navigate(NavDestinations.BOOKS_MANAGEMENT_ROUTE) }
                        )
                        QuickActionButton(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.ic_cart),
                            label = "Manage Orders",
                            onClick = { navController.navigate(NavDestinations.ORDERS_MANAGEMENT_ROUTE) }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionButton(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.ic_clipboard),
                            label = "Inventory",
                            onClick = { navController.navigate(NavDestinations.INVENTORY_ADD_BOOK_ROUTE) }
                        )
                        QuickActionButton(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.ic_stats),
                            label = "Statistics",
                            onClick = { navController.navigate(NavDestinations.STATISTICS_ROUTE) }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuickActionButton(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(id = R.drawable.ic_settings),
                            label = "Settings",
                            onClick = { navController.navigate(NavDestinations.SETTINGS_PROFILE_ROUTE) }
                        )
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))

                // 3. SYSTEM INFORMATION SECTION
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = LivriaYellowLight.copy(alpha = 0.35f),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 24.dp)
                ) {
                    Column(modifier = Modifier.padding(3.dp)) {
                        Text(
                            "System Information",
                            textAlign = TextAlign.Center,
                            color = LivriaNavyBlue,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier.fillMaxWidth()
                                .padding(top= 18.dp)
                        )
                        Text(
                            "Application Version: 1.0.0",
                            textAlign = TextAlign.Center,
                            color = LivriaBlack,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontFamily = AlexandriaFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 11.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(3.dp)
                                .padding(top = 10.dp)
                        )
                        Text(
                            "Server Status: Online",
                            textAlign = TextAlign.Center,
                            color = LivriaBlack,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontFamily = AlexandriaFontFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 11.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(3.dp)
                                .padding(bottom = 18.dp)
                        )
                    }
                }

            }



        }


    }
}

@Composable
fun QuickActionButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    label: String,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .padding(6.dp)
            .height(50.dp)
            .width(150.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = LivriaWhite),
        border = BorderStroke(1.dp, LivriaLightGray)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = label,
                tint = LivriaOrange,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = label,
                textAlign = TextAlign.Start,
                color = LivriaBlack,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = AlexandriaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
        }
    }
}