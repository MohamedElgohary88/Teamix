package com.chocolate.presentation.screens.createtopic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chocolate.presentation.R
import com.chocolate.presentation.composable.TeamixButton
import com.chocolate.presentation.composable.TeamixScaffold
import com.chocolate.presentation.composable.TeamixTextField
import com.chocolate.presentation.screens.createchannel.composable.ActionSnakeBar
import com.chocolate.presentation.screens.topicmessages.navigateToTopic
import com.chocolate.presentation.theme.SpacingXXLarge
import com.chocolate.presentation.theme.TeamixTheme
import com.chocolate.presentation.theme.customColors
import com.chocolate.presentation.util.CollectUiEffect
import com.chocolate.presentation.util.LocalNavController
import com.chocolate.viewmodel.createtopic.CreateTopicEffect
import com.chocolate.viewmodel.createtopic.CreateTopicInteraction
import com.chocolate.viewmodel.createtopic.CreateTopicUiState
import com.chocolate.viewmodel.createtopic.CreateTopicViewModel

@Composable
fun CreateTopicScreen(
    createTopicViewModel: CreateTopicViewModel = hiltViewModel(),
    navController: NavController = LocalNavController.current
) {
    val state by createTopicViewModel.state.collectAsState()

    CollectUiEffect(createTopicViewModel.effect) { createTopicEffect ->
        when (createTopicEffect) {
            is CreateTopicEffect.NavigateToTopicScreen -> {
                navController.popBackStack()
                navController.navigateToTopic(
                    channelId = createTopicEffect.channelId.toInt(),
                    topicId = createTopicEffect.topicId,
                    topicName = createTopicEffect.topicName,
                )
            }
        }
    }

    CreateChannelContent(
        state,
        createTopicViewModel,
    )

}

@Composable
fun CreateChannelContent(
    state: CreateTopicUiState,
    createTopicInteraction: CreateTopicInteraction,
) {
    val colors = MaterialTheme.customColors()

    TeamixScaffold(
        hasBackArrow = true,
        title = stringResource(id = R.string.create_topic),
        statusBarColor = colors.card,
        hasAppBar = true,
        containerColorAppBar = colors.card,
        titleColor = colors.onBackground87,
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background(colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextInputField(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp, 16.dp, 16.dp, 16.dp),
                textInputLabel = stringResource(R.string.topic_name),
                onValueChange = createTopicInteraction::onTopicNameChange,
                value = state.name
            )

            TextInputField(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 16.dp, end = 8.dp, bottom = 24.dp),
                textInputLabel = stringResource(R.string.topic_content),
                onValueChange = createTopicInteraction::onTopicContentChange,
                value = state.content,

                )

            TeamixButton(
                onClick = createTopicInteraction::onCreateClick,
                colors = colors,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                AnimatedVisibility(visible = state.isLoading) {
                    CircularProgressIndicator(
                        color = colors.card,
                        modifier = Modifier
                            .size(SpacingXXLarge)
                            .align(Alignment.CenterVertically)
                    )
                }
                AnimatedVisibility(visible = !state.isLoading) {
                    Text(
                        text = stringResource(R.string.create),
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.onPrimary
                    )
                }


            }
            Spacer(modifier = Modifier.weight(1f))
            state.message?.let {
                ActionSnakeBar(
                    isVisible = true,
                    contentMessage = it,
                    onClick = createTopicInteraction::onErrorDismiss,
                    actionTitle = stringResource(id = R.string.dismiss)
                )
            }
        }
    }
}

@Preview
@Composable
fun CreateChannelContentPreview() {
    TeamixTheme {
        CreateTopicScreen()
    }
}

@Composable
private fun TextInputField(
    modifier: Modifier = Modifier,
    textInputLabel: String,
    onValueChange: (String) -> Unit,
    value: String
) {

    Column(modifier) {
        Text(
            text = textInputLabel,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.customColors().onBackground87
        )
        TeamixTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            singleLine = true
        )
    }
}