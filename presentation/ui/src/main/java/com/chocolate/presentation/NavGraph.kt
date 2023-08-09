package com.chocolate.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.chocolate.presentation.on_boarding.onboardingRoute
import com.chocolate.presentation.screens.login.loginRoute
import com.chocolate.presentation.screens.organiztion.organizationNameRoute
import com.chocolate.presentation.welcome.welcomeRoute

@Composable
fun SetUpNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.OrganizationName.route) {
        welcomeRoute(navController)
        onboardingRoute(navController)
        organizationNameRoute(navController)
        loginRoute(navController)
    }
}