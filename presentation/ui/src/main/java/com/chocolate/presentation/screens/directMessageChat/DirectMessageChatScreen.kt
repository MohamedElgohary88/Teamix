package com.chocolate.presentation.screens.directMessageChat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.chocolate.presentation.composable.MyReplyMessage
import com.chocolate.presentation.composable.ReplyMessage
import com.chocolate.presentation.composable.StartNewMessage
import com.chocolate.presentation.composable.TeamixScaffold
import com.chocolate.presentation.theme.SpacingXLarge
import com.chocolate.viewmodel.directMessageChat.DirectMessagesChatViewModel
import com.chocolate.viewmodel.topicMessages.TopicMessagesInteraction
import com.chocolate.viewmodel.topicMessages.TopicUiState

@Composable
fun DirectMessageChatScreen(viewModel: DirectMessagesChatViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    DirectMessageChatContent(state , viewModel)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DirectMessageChatContent(state: TopicUiState, interaction: TopicMessagesInteraction) {
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = state.messages.size) {
        state.messages.takeIf { messages ->
            messages.isNotEmpty()
        }?.let {
            scrollState.animateScrollToItem(0)
        }

    }
    TeamixScaffold(
        title = state.topicName,
        isDarkMode = isSystemInDarkTheme(),
        hasAppBar = true,
        hasBackArrow = true,
        bottomBar = {
            StartNewMessage(
                openEmojisTile = {
                                 },
                onMessageInputChanged = { interaction.onMessageInputChanged(it) },
                onSendMessage = { interaction.onSendMessage(state.messageInput) },
                onStartVoiceRecording = { },
                onClickCamera = {  },
                onClickPhotoOrVideo = {  },
                photoOrVideoList = state.photoAndVideo,
                modifier = Modifier,
                messageInput = state.messageInput,
            )
        }
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            val (messages) = createRefs()
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxHeight()
                    .constrainAs(messages) { bottom.linkTo(parent.bottom) },
                reverseLayout = true,
                verticalArrangement = Arrangement.spacedBy(SpacingXLarge),
                contentPadding = PaddingValues(bottom = SpacingXLarge, top = SpacingXLarge)
            ) {
                items(state.messages.size) {
                    if (state.messages[it].isMyReplay)
                        MyReplyMessage(
                            modifier = Modifier.animateItemPlacement(),
                            messageUiState = state.messages[it],
                            onAddReactionToMessage = {  },
                            onGetNotification = { },
                            onPinMessage = {  },
                            onSaveMessage = {  },
                            onClickReact = { positive, state ->

                            }
                        )
                    else
                        ReplyMessage(
                            modifier = Modifier.animateItemPlacement(),
                            messageUiState = state.messages[it],
                            onAddReactionToMessage = { },
                            onGetNotification = { },
                            onPinMessage = {  },
                            onSaveMessage = {  },
                            onOpenReactTile = {  },
                            onClickReact = { positive, state ->

                            }
                        )
                }
            }
        }
    }
}