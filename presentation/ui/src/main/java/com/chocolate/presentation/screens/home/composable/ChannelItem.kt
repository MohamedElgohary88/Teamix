package com.chocolate.presentation.screens.home.composable

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import com.chocolate.presentation.R
import com.chocolate.presentation.theme.CustomColorsPalette
import com.chocolate.presentation.theme.Float1
import com.chocolate.presentation.theme.Radius12
import com.chocolate.presentation.theme.Radius8
import com.chocolate.presentation.theme.SpacingMedium
import com.chocolate.presentation.theme.SpacingXLarge
import com.chocolate.viewmodel.home.ChannelUiState

@SuppressLint("RememberReturnType")
@Composable
fun ChannelItem(
    modifier: Modifier = Modifier,
    state: ChannelUiState,
    colors: CustomColorsPalette,
    contentDescription: String = "",
    onClickTopic: (String, String, String) -> Unit,
    onClickItemChannel: (String, String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val animateIcon by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .clip(RoundedCornerShape(Radius12))
            .clickable { onClickItemChannel(state.channelId, state.name) }
            .background(color = colors.card)
            .padding(SpacingXLarge), verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hashtag),
                contentDescription = contentDescription,
                tint = colors.primary,
                modifier = Modifier.padding(end = SpacingMedium)
            )
            Text(
                text = state.name,
                style = MaterialTheme.typography.labelLarge,
                color = colors.onBackground87,
                modifier = Modifier.weight(Float1)
            )
            if (state.topics.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = contentDescription,
                    tint = colors.onBackground60,
                    modifier = Modifier
                        .rotate(animateIcon)
                        .clickable { isExpanded = !isExpanded }

                )
            }
        }
        if (isExpanded) {
            state.topics.forEach { topicUIState ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onClickItemChannel(state.channelId, state.name)
                        },
                    verticalArrangement = Arrangement.Center
                ) {
                    Divider(modifier = Modifier.padding(SpacingMedium), color = colors.border)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .pointerInput(Unit) {
                                detectTapGestures(onPress = {
                                    onClickTopic(
                                        state.channelId,
                                        topicUIState.id,
                                        topicUIState.name
                                    )
                                })
                            }
                            .padding(vertical = Radius8),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = topicUIState.name, color = colors.onBackground60)
                    }
                }
            }
        }
    }
}