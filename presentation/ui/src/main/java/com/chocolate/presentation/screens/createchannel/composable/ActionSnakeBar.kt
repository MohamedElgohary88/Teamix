package com.chocolate.presentation.screens.createchannel.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.chocolate.presentation.theme.ButtonSize32
import com.chocolate.presentation.theme.DarkCard
import com.chocolate.presentation.theme.Float3
import com.chocolate.presentation.theme.LightCard
import com.chocolate.presentation.theme.Radius12
import com.chocolate.presentation.theme.SpacingXLarge
import com.chocolate.presentation.theme.SpacingXXSmall
import com.chocolate.presentation.theme.customColors
import kotlinx.coroutines.delay

@Composable
fun ActionSnakeBar(
    contentMessage: String,
    modifier: Modifier = Modifier,
    isVisible: Boolean = false,
    onDismiss: () -> Unit = {},
    onClick: () -> Unit = {},
    isToggleButtonVisible: Boolean = true,
    actionTitle: String = "",
) {
    val colors = MaterialTheme.customColors()
    val textStyle = MaterialTheme.typography

    var timeVisible by remember { mutableStateOf(isVisible) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            timeVisible = true
            delay(2000)
            onClick()
            onDismiss()
            timeVisible = false
        } else {
            timeVisible = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(SpacingXLarge)
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            visible = timeVisible
        ) {
            Snackbar(shape = RoundedCornerShape(Radius12), containerColor = DarkCard) {
                Row(
                    modifier = Modifier.padding(vertical = SpacingXXSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(Float3),
                        text = contentMessage,
                        style = textStyle.bodyMedium,
                        color = LightCard
                    )
                    AnimatedVisibility(isToggleButtonVisible) {
                        ToggleButton(
                            modifier = Modifier
                                .height(ButtonSize32)
                                .weight(1f),
                            color = colors.primary,
                            isFilled = true,
                            onClick = { onClick() }) {
                            Text(
                                text = actionTitle,
                                style = textStyle.bodyMedium,
                                color = LightCard
                            )
                        }
                    }
                }
            }
        }
    }

}