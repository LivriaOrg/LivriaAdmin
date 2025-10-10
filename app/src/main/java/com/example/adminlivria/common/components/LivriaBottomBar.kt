package com.example.adminlivria.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.adminlivria.R
import com.example.adminlivria.common.navigation.NavDestinations
import com.example.adminlivria.common.ui.theme.LivriaOrange
import com.example.adminlivria.common.ui.theme.LivriaAmber
import com.example.adminlivria.common.ui.theme.LivriaBlue
import com.example.adminlivria.common.ui.theme.LivriaLightGray
import com.example.adminlivria.common.ui.theme.LivriaNavyBlue
import com.example.adminlivria.common.ui.theme.LivriaSoftCyan
import com.example.adminlivria.common.ui.theme.LivriaYellowLight





sealed class BottomNavItem(
    val title: String,
    val iconId: Int,
    val route: String
) {
    object Home : BottomNavItem("Home", R.drawable.ic_home, NavDestinations.HOME_ROUTE)
    object Books : BottomNavItem("Books", R.drawable.ic_book, NavDestinations.BOOKS_MANAGEMENT_ROUTE)
    object Orders : BottomNavItem("Orders", R.drawable.ic_cart, NavDestinations.ORDERS_MANAGEMENT_ROUTE)
    object Inventory : BottomNavItem("Inventory", R.drawable.ic_clipboard, NavDestinations.INVENTORY_ADD_BOOK_ROUTE)
    object Stats : BottomNavItem("Stats", R.drawable.ic_stats, NavDestinations.STATISTICS_ROUTE)
}

val items = listOf(
    BottomNavItem.Home,
    BottomNavItem.Books,
    BottomNavItem.Orders,
    BottomNavItem.Inventory,
    BottomNavItem.Stats
)


@Composable
fun LivriaBottomNavBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val backgroundColor = MaterialTheme.colorScheme.tertiary


    Column(
        Modifier.fillMaxWidth()
            .navigationBarsPadding()
    ) {

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


        NavigationBar(
            containerColor = backgroundColor,
            contentColor = Color.White,
            tonalElevation = 0.dp
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route

                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconId),
                            contentDescription = item.title,
                            modifier = Modifier.height(24.dp)
                        )
                    },
                    label = null,
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LivriaAmber,
                        unselectedIconColor = Color.White,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}