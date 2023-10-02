package com.chocolate.presentation.screens.directmessagechat

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chocolate.presentation.Screen
import com.chocolate.viewmodel.directmessagechat.DirectMessageChatArgs

fun NavGraphBuilder.directMessageChatRoute(){
    composable(
        route = "${Screen.DirectMessageChat.route}/{${DirectMessageChatArgs.GROUP_ID}}/{${DirectMessageChatArgs.MEMBER_NAME}}",
        arguments = listOf(
            navArgument(DirectMessageChatArgs.GROUP_ID) { NavType.StringType },
            navArgument(DirectMessageChatArgs.MEMBER_NAME) { NavType.StringType },
        )
    ){
        DirectMessageChatScreen()
    }
}

fun NavController.navigateToDirectMessageChat(
    groupId: String,
    memberName: String,
    popBackStack:Boolean = false
){
    if (popBackStack){popBackStack()}
    navigate("${Screen.DirectMessageChat.route}/$groupId/$memberName")
}