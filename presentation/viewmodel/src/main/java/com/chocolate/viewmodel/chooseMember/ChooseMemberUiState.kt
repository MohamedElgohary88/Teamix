package com.chocolate.viewmodel.chooseMember

import com.chocolate.viewmodel.base.BaseViewModel

data class ChooseMemberUiState(
    val searchQuery: String = "",
    val selectedMembersUiState: List<SelectedMembersUiState> = emptyList(),
    val membersUiState: List<ChooseMembersUiState> = emptyList(),
    val channelName: String = "",
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val error: String? = null
): BaseViewModel.BaseUiState

data class SelectedMembersUiState(
    val userId: Int = 0,
    val imageUrl: String ="",
    val name: String = "",
    val isClickedForRemoval: Boolean = false
)

data class ChooseMembersUiState(
    val userId: Int = 0,
    val imageUrl: String ="",
    val name: String = "",
    val status: String = "",
    val isSelected: Boolean = false
)