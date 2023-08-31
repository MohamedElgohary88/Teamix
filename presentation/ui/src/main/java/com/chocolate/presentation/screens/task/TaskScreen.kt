package com.chocolate.presentation.screens.task

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.chocolate.presentation.composable.TeamixScaffold
import com.chocolate.presentation.screens.create_channel.composable.ActionSnakeBar
import com.chocolate.presentation.util.LocalNavController

@Composable
fun TaskScreen() {
    val navController = LocalNavController.current
    TaskContent()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskContent() {
    TeamixScaffold(isDarkMode = isSystemInDarkTheme()) {
        ActionSnakeBar(
            contentMessage = "Task Screen",
            isVisible = true,
            isToggleButtonVisible = false
        )

    }
}
