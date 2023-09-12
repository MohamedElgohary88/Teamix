package com.chocolate.viewmodel.topic

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.chocolate.entities.messages.Message
import com.chocolate.entities.uills.Empty
import com.chocolate.usecases.message.ManageTopicMessagesUseCase
import com.chocolate.usecases.user.GetCurrentUserDataUseCase
import com.chocolate.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val manageTopicMessagesUseCase: ManageTopicMessagesUseCase,
    private val getCurrentUserDataUseCase: GetCurrentUserDataUseCase
) : BaseViewModel<TopicUiState, TopicEffect>(TopicUiState()), TopicInteraction {

    private val topicArgs = TopicArgs(savedStateHandle)

    init {
        _state.update { it.copy(topicName = topicArgs.topicName) }
        getAllMessages()

    }

    override fun onClickBackButton() {
        sendUiEffect(TopicEffect.NavigationBack)
    }

    override fun openEmojisTile() {

    }

    override fun onMessageInputChanged(text: String) {
        _state.update { it.copy(messageInput = text) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSendMessage(text: String) {
        viewModelScope.launch {
            val id = getCurrentUserDataUseCase.invoke().id
            val name = getCurrentUserDataUseCase.invoke().fullName
            val imageUrl = getCurrentUserDataUseCase.invoke().imageUrl
            val message = Message(
                id = "",
                senderId = id.toString(),
                senderFullName = name,
                senderAvatarUrl = imageUrl,
                messageContent = text,
                timestamp = Date.from(
                    LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
                )
            )
            manageTopicMessagesUseCase.sendMessage(
                message = message,
                channelId = topicArgs.channelId.toString(),
                topicId = topicArgs.topicId.toString()
            )
        }
        _state.update { it.copy(messageInput = String.Empty) }
    }

    private fun getAllMessages() {
        viewModelScope.launch {
            tryToExecuteFlow({
                manageTopicMessagesUseCase.getMessages(
                    topicArgs.topicId.toString(),
                    topicArgs.channelId.toString()
                )
            }, ::onSuccessGetMessage, ::onError)
        }
    }

    private fun onError(throwable: Throwable) {
        _state.update { it.copy(error = throwable.message) }
    }

    private suspend fun onSuccessGetMessage(flow: Flow<List<Message>>) {
        flow.collectLatest { messages ->
            messages.map {
                _state.update {
                    it.copy(
                        messages = messages.toUiState(false)
                    )
                }
            }
        }
    }


    override fun onStartVoiceRecording() {

    }

    override fun onClickCamera() {

    }

    override fun onClickPhotoOrVideo(position: Int) {

    }

    override fun onAddReactionToMessage(messageId: Int) {

    }

    override fun onSaveMessage() {

    }

    override fun onGetNotification() {

    }

    override fun onPinMessage() {

    }

    override fun onOpenReactTile() {

    }

    override fun onClickReact(positive: Boolean, reactionUiState: ReactionUiState) {

    }

}