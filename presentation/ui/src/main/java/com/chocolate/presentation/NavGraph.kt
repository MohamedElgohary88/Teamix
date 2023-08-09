package com.chocolate.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.chocolate.presentation.on_boarding.onboardingRoute
import com.chocolate.presentation.screens.organiztion.organizationNameRoute
import com.chocolate.presentation.welcome.welcomeRoute

@Composable
fun SetUpNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        welcomeRoute(navController)
        onboardingRoute(navController)
        organizationNameRoute(navController)
    }
}