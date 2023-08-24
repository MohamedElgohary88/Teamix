package com.chocolate.presentation.screens.chooseMember

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chocolate.presentation.Screen
import com.chocolate.viewmodel.createChannel.CreateChannelArgs

fun NavGraphBuilder.chooseMemberRoute(){
    composable(
        route = "${Screen.ChooseMembers.route}/{${CreateChannelArgs.CHANNEL_NAME}}",
        arguments = listOf(navArgument(CreateChannelArgs.CHANNEL_NAME) { NavType.StringType })
    ){
        ChooseMemberScreen()
    }
}

fun NavController.navigateToChooseMember(channelName: String){
    navigate("${Screen.ChooseMembers.route}/$channelName")
}