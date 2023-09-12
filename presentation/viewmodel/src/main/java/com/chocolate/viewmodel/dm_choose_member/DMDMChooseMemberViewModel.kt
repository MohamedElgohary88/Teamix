package com.chocolate.viewmodel.dm_choose_member

import androidx.lifecycle.viewModelScope
import com.chocolate.entities.exceptions.NoConnectionException
import com.chocolate.entities.member.Member
import com.chocolate.usecases.member.GetMembersInOrganizationUseCase
import com.chocolate.viewmodel.base.BaseViewModel
import com.chocolate.viewmodel.base.StringsResource
import com.chocolate.viewmodel.chooseMember.ChooseMembersUiState
import com.chocolate.viewmodel.chooseMember.toUsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DMDMChooseMemberViewModel @Inject constructor(
    private val getAllUsers: GetMembersInOrganizationUseCase,
    private val stringsResource: StringsResource
) : BaseViewModel<DMChooseMemberUiState, Unit>(DMChooseMemberUiState()), DMChooseMemberInteraction {

    init {
        getUsers()
    }

    private fun getUsers() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(getAllUsers::invoke, ::onGetUsersSuccess, ::onGetUsersError)
    }

    private fun onGetUsersSuccess(members: Flow<List<Member>>) {
        viewModelScope.launch {
            members.collectLatest {members->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = null,
                        membersUiState = members.toUsersUiState()
                    )
                }
            }

        }
    }
    private fun onGetUsersError(throwable: Throwable) {
        _state.update {
            it.copy(
                error = getMessageError(throwable),
                isLoading = false,
                successMessage = null
            )
        }
    }
    private fun getMessageError(throwable: Throwable): String {
        return when (throwable) {
            is NoConnectionException -> stringsResource.noConnectionMessage
            else -> stringsResource.globalMessageError
        }
    }
}