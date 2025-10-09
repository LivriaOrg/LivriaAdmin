package com.example.adminlivria.common.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.adminlivria.profilecontext.domain.AdminUser
import com.example.adminlivria.common.components.LivriaBottomNavBar
import com.example.adminlivria.common.components.LivriaTopBar
import com.example.adminlivria.searchcontext.presentation.HomeScreen
import com.example.adminlivria.profilecontext.presentation.SettingsScreen
import com.example.adminlivria.profilecontext.presentation.LoginScreen
import com.example.adminlivria.orderscontext.presentation.OrdersScreen

@Composable
fun AdminNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBars = currentRoute != NavDestinations.LOGIN_ROUTE

    val adminUser = AdminUser.mock()

    Scaffold(
        topBar = {
            if (showBars) {
                LivriaTopBar(navController = navController, currentRoute = currentRoute, currentUser = adminUser)
            }
        },
        bottomBar = {
            if (showBars) {
                LivriaBottomNavBar(navController = navController)
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = NavDestinations.ORDERS_MANAGEMENT_ROUTE,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(route = NavDestinations.LOGIN_ROUTE) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(NavDestinations.HOME_ROUTE) {
                            popUpTo(NavDestinations.LOGIN_ROUTE) { inclusive = true }
                        }
                    }
                )
            }

            // 1. HOME (Rutas que requieren autenticación)
            composable(route = NavDestinations.HOME_ROUTE) {
                HomeScreen(navController = navController)
            }

            // 2. SETTINGS (RUTA BARRA SUPERIOR)
            composable(route = NavDestinations.SETTINGS_PROFILE_ROUTE) {
                SettingsScreen()
            }

            // 3. RUTAS DE LA BARRA INFERIOR
            composable(route = NavDestinations.BOOKS_MANAGEMENT_ROUTE) {
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    Toast.makeText(context, "BOOKS!", Toast.LENGTH_SHORT).show()
                }
            }
            composable(route = NavDestinations.ORDERS_MANAGEMENT_ROUTE) {
                OrdersScreen(navController = navController)
            }
            composable(route = NavDestinations.INVENTORY_ADD_BOOK_ROUTE) {
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    Toast.makeText(context, "INVENTORY!", Toast.LENGTH_SHORT).show()
                }
            }
            composable(route = NavDestinations.STATISTICS_ROUTE) {
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    Toast.makeText(context, "STATS!", Toast.LENGTH_SHORT).show()
                }
            }

            // 4. RUTAS DETALLE
            composable(route = NavDestinations.BOOK_DETAIL_ROUTE) {  }
            composable(route = NavDestinations.ORDER_DETAIL_ROUTE) {  }
            composable(route = NavDestinations.INVENTORY_INDIVIDUAL_STOCK_ROUTE) {  }
        }
    }
}