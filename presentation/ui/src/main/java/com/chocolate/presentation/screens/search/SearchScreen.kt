package com.chocolate.presentation.screens.search

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.chocolate.presentation.composable.TeamixScaffold
import com.chocolate.presentation.util.LocalNavController

@Composable
fun SearchScreen() {
    val navController = LocalNavController.current
    SearchContent()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchContent() {
    val context = LocalContext.current
    TeamixScaffold {
        Toast.makeText(context, "Search Screen", Toast.LENGTH_SHORT).show()
    }
}
