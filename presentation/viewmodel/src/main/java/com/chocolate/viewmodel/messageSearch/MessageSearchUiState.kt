package com.chocolate.viewmodel.messageSearch

import com.chocolate.viewmodel.base.BaseErrorUiState
import com.chocolate.viewmodel.pinnedMessages.MessageItemUiState

data class MessageSearchUiState(
    val searchInput: String = "",
    val messages: List<MessageItemUiState> = emptyList(),
    val isLoading: Boolean = false,
    val error: BaseErrorUiState = BaseErrorUiState()
)

