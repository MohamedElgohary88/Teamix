package com.chocolate.presentation.screens.direct_message

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.chocolate.presentation.composable.TeamixScaffold
import com.chocolate.presentation.screens.create_channel.composable.ActionSnakeBar
import com.chocolate.presentation.theme.customColors
import com.chocolate.presentation.util.LocalNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun DirectMessageScreen() {
    val navController = LocalNavController.current
    DirectMessageContent()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DirectMessageContent() {
    val context = LocalContext.current
    TeamixScaffold(isDarkMode = isSystemInDarkTheme()) {
        val colors = MaterialTheme.customColors()
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(color = Color.Black, darkIcons = false)

        ActionSnakeBar(
            contentMessage = "Direct Message Screen",
            isVisible = true,
            isToggleButtonVisible = false
        )
    }
}
