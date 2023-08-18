package com.chocolate.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.chocolate.presentation.screens.login.loginRoute
import com.chocolate.presentation.screens.organiztion.organizationNameRoute
import com.chocolate.presentation.screens.profile.profileRoute
import com.chocolate.presentation.screens.on_boarding.onboardingRoute
import com.chocolate.presentation.screens.create_organization.createOrganizationWebViewRoute
import com.chocolate.presentation.screens.forget_password.forgetPasswordWebViewRoute
import com.chocolate.presentation.screens.home.homeRoute
import com.chocolate.presentation.screens.oner_power.ownerPowerRoute
import com.chocolate.presentation.screens.welcome.welcomeRoute
import com.chocolate.viewmodel.main.MainViewModel

@Composable
fun SetUpNavGraph(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        welcomeRoute(navController)
        onboardingRoute(navController)
        homeRoute(navController,mainViewModel)
        organizationNameRoute(navController)
        createOrganizationWebViewRoute(navController)
        loginRoute(navController)
        forgetPasswordWebViewRoute(navController)
        profileRoute(navController,mainViewModel)
        ownerPowerRoute(navController)
    }
}