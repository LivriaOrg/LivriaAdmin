package com.example.adminlivria.presentation.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.adminlivria.common.authServiceInstance
import com.example.adminlivria.data.local.TokenManager
import com.example.adminlivria.domain.profilecontext.entity.AdminUser
import com.example.adminlivria.presentation.components.LivriaBottomNavBar
import com.example.adminlivria.presentation.components.LivriaTopBar
import com.example.adminlivria.presentation.home.HomeScreen
import com.example.adminlivria.presentation.settings.SettingsScreen
import com.example.adminlivria.presentation.login.LoginScreen
import com.example.adminlivria.presentation.login.LoginViewModel
import com.example.adminlivria.presentation.login.LoginViewModelFactory
import com.example.adminlivria.common.initializeTokenManager
import com.example.adminlivria.common.userAdminServiceInstance
import com.example.adminlivria.presentation.settings.SettingsViewModel
import com.example.adminlivria.presentation.settings.SettingsViewModelFactory

@Composable
fun AdminNavGraph(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    initializeTokenManager(context)
    val tokenManager = TokenManager(context)
    val loginViewModelFactory = LoginViewModelFactory(
        authService = authServiceInstance,
        tokenManager = tokenManager
    )

    val settingsViewModelFactory = SettingsViewModelFactory(
        userAdminService = userAdminServiceInstance,
        tokenManager = tokenManager
    )


    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided."
    }

    val settingsViewModel: SettingsViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = settingsViewModelFactory
    )

    val settingsState = settingsViewModel.uiState

    val adminUser = AdminUser.mock().copy(
        capital = settingsState.capital
    )
    // ----------------------------------------

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBars = currentRoute != NavDestinations.LOGIN_ROUTE

    Scaffold(
        topBar = {
            if (showBars) {
                LivriaTopBar(
                    navController = navController,
                    currentRoute = currentRoute,
                    currentUser = adminUser
                )
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
            startDestination = NavDestinations.LOGIN_ROUTE,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(route = NavDestinations.LOGIN_ROUTE) {
                val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginSuccess = {
                        settingsViewModel.loadAdminData()
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
                SettingsScreen(
                    viewModel = settingsViewModel,
                    onLogout = {
                        navController.navigate(NavDestinations.LOGIN_ROUTE) {
                            popUpTo(NavDestinations.HOME_ROUTE) { inclusive = true }
                        }
                    }
                )
            }

            // 3. RUTAS DE LA BARRA INFERIOR
            composable(route = NavDestinations.BOOKS_MANAGEMENT_ROUTE) {
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    Toast.makeText(context, "BOOKS!", Toast.LENGTH_SHORT).show()
                }
            }
            composable(route = NavDestinations.ORDERS_MANAGEMENT_ROUTE) {
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    Toast.makeText(context, "ORDERS!", Toast.LENGTH_SHORT).show()
                }            }
            composable(route = NavDestinations.INVENTORY_ADD_BOOK_ROUTE) {
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    Toast.makeText(context, "INVENTORY!", Toast.LENGTH_SHORT).show()
                }            }
            composable(route = NavDestinations.STATISTICS_ROUTE) {
                val context = LocalContext.current

                LaunchedEffect(Unit) {
                    Toast.makeText(context, "STATS!", Toast.LENGTH_SHORT).show()
                }            }

            // 4. RUTAS DETALLE
            composable(route = NavDestinations.BOOK_DETAIL_ROUTE) {  }
            composable(route = NavDestinations.ORDER_DETAIL_ROUTE) {  }
            composable(route = NavDestinations.INVENTORY_INDIVIDUAL_STOCK_ROUTE) {  }
        }
    }
}
