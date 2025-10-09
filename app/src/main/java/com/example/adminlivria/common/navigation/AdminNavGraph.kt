package com.example.adminlivria.common.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.adminlivria.common.components.LivriaBottomNavBar
import com.example.adminlivria.common.components.LivriaTopBar
import com.example.adminlivria.profilecontext.domain.AdminUser
import com.example.adminlivria.profilecontext.presentation.LoginScreen
import com.example.adminlivria.profilecontext.presentation.LoginViewModel
import com.example.adminlivria.profilecontext.presentation.LoginViewModelFactory
import com.example.adminlivria.profilecontext.presentation.SettingsScreen
import com.example.adminlivria.profilecontext.presentation.SettingsViewModel
import com.example.adminlivria.profilecontext.presentation.SettingsViewModelFactory
import com.example.adminlivria.searchcontext.presentation.HomeScreen
import com.example.adminlivria.orderscontext.presentation.OrdersScreen


import com.example.adminlivria.profilecontext.data.local.TokenManager
import com.example.adminlivria.common.authServiceInstance
import com.example.adminlivria.common.userAdminServiceInstance
import com.example.adminlivria.common.initializeTokenManager


import com.example.adminlivria.bookcontext.presentation.BooksScreen
import com.example.adminlivria.bookcontext.presentation.BooksManagementViewModel
import com.example.adminlivria.bookcontext.presentation.BooksViewModelFactory
import com.example.adminlivria.bookcontext.presentation.detail.BookDetailScreen

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

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val settingsViewModel: SettingsViewModel = viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = settingsViewModelFactory
    )
    val settingsState by settingsViewModel.uiState.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBars = currentRoute != NavDestinations.LOGIN_ROUTE

    val adminUser = AdminUser.mock().copy(capital = settingsState.capital)


    val booksViewModel: BooksManagementViewModel = viewModel(
        factory = BooksViewModelFactory(context)
    )

    Scaffold(
        topBar = { if (showBars) LivriaTopBar(navController, currentRoute, adminUser) },
        bottomBar = { if (showBars) LivriaBottomNavBar(navController) }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = if (tokenManager.getToken() != null)
                NavDestinations.HOME_ROUTE else NavDestinations.LOGIN_ROUTE,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(NavDestinations.LOGIN_ROUTE) {
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


            composable(NavDestinations.HOME_ROUTE) {
                HomeScreen(navController = navController)
            }


            composable(NavDestinations.SETTINGS_PROFILE_ROUTE) {
                SettingsScreen(
                    viewModel = settingsViewModel,
                    onLogout = {
                        settingsViewModel.logout()
                        navController.navigate(NavDestinations.LOGIN_ROUTE) {
                            popUpTo(NavDestinations.HOME_ROUTE) { inclusive = true }
                        }
                    }
                )
            }


            composable(NavDestinations.BOOKS_MANAGEMENT_ROUTE) {
                BooksScreen(
                    navController = navController,     // 👈 necesario para navegar al detalle
                    viewModel = booksViewModel
                )
            }


            composable(NavDestinations.ORDERS_MANAGEMENT_ROUTE) {
                OrdersScreen(navController = navController)
            }

            composable(NavDestinations.INVENTORY_ADD_BOOK_ROUTE) { /* TODO */ }
            composable(NavDestinations.STATISTICS_ROUTE) { /* TODO */ }


            composable("${NavDestinations.BOOK_DETAIL_ROUTE}/{bookId}") { backStack ->
                val id = backStack.arguments?.getString("bookId")?.toIntOrNull() ?: return@composable
                BookDetailScreen(bookId = id)
            }



        }
    }
}
