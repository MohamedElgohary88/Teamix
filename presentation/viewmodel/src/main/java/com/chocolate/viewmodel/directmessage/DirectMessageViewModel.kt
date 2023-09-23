package com.chocolate.viewmodel.directmessage

import androidx.lifecycle.viewModelScope
import com.chocolate.usecases.directmessage.GetAllChatsByIdUseCase
import com.chocolate.usecases.directmessage.SearchInDirectMessageChatsUseCase
import com.chocolate.usecases.member.GetCurrentMemberUseCase
import com.chocolate.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectMessageViewModel @Inject constructor(
    private val getAllChatsByIdUseCase: GetAllChatsByIdUseCase,
    private val getCurrentMemberUseCase: GetCurrentMemberUseCase,
    private val searchInDirectMessageChatsUseCase: SearchInDirectMessageChatsUseCase
) : BaseViewModel<DirectMessageUiState, DirectMessageUiEffect>(DirectMessageUiState()),
    DirectMessageInteractions {

    init {
        viewModelScope.launch {
            val currentMemberId = getCurrentMemberUseCase().id
            getAllChatsByIdUseCase(currentMemberId)
                .combine(state.value.searchInput) { chats, searchQuery ->
                    searchInDirectMessageChatsUseCase(chats, searchQuery)
                }.collect {
                    _state.update { uiState ->
                        uiState.copy(chats = it.toUiState())
                    }
                }
        }
    }

    override fun onChangeSearchQuery(searchQuery: String) {
        viewModelScope.launch { _state.value.searchInput.emit(searchQuery) }
    }

    override fun onClickDeleteQuery() {
        viewModelScope.launch { _state.value.searchInput.emit("") }
    }

    override fun onClickNewChat() {
        sendUiEffect(DirectMessageUiEffect.NavigateToChooseMember)
    }

    override fun onClickChat(id: String, name: String) {
        sendUiEffect(DirectMessageUiEffect.NavigateToChat(id, name))
    }
}