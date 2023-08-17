package com.chocolate.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chocolate.presentation.screens.main_screen.BottomNavigationItem
import com.chocolate.presentation.screens.main_screen.BottomNavigationNavGraph
import com.chocolate.presentation.screens.main_screen.composables.MainScreenBottomNavigation
import com.chocolate.presentation.theme.TeamixTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TeamixTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val shouldShowBottomNavigation = when (navBackStackEntry?.destination?.route) {
                    BottomNavigationItem.Home.screen_route,
                    BottomNavigationItem.Profile.screen_route,
                    BottomNavigationItem.DMs.screen_route,
                    BottomNavigationItem.Tasks.screen_route,
                    BottomNavigationItem.Search.screen_route -> true
                    else -> false
                }
                Scaffold(
                    bottomBar = { if(shouldShowBottomNavigation)
                        MainScreenBottomNavigation(navController =navController )
                 }
                ) { padding->
                    BottomNavigationNavGraph(navController = navController)
                }
            }
        }
    }
}
