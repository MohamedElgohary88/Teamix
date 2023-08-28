package com.chocolate.presentation.screens.bottomNavigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chocolate.presentation.theme.customColors

@Composable
fun currentRoute(navController:NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Search,
        BottomNavigationItem.Tasks,
        BottomNavigationItem.DMs,
        BottomNavigationItem.Profile
    )
    val color=MaterialTheme.customColors()

    NavigationBar(
        containerColor = color.card
    ) {

        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = color.onPrimary,
                    selectedTextColor = color.primary,
                    indicatorColor = color.primary,
                    unselectedIconColor = color.onBackground60,
                    unselectedTextColor = color.onBackground60,
                ),
                selected = currentRoute(navController) == item.screenRoute,
                alwaysShowLabel = false,
                label = {
                    Text(
                        text = stringResource(id = item.title),
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title)
                    )
                }
            )
        }
    }
}