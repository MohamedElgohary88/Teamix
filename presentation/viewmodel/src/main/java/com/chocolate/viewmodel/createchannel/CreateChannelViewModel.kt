package com.chocolate.viewmodel.createchannel

import android.util.Log
import com.chocolate.entities.util.InvalidChannelNameException
import com.chocolate.entities.util.NoConnectionException
import com.chocolate.usecases.channel.ManageChannelUseCase
import com.chocolate.viewmodel.base.BaseViewModel
import com.chocolate.viewmodel.base.StringsResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateChannelViewModel @Inject constructor(
    private val manageChannelUseCase: ManageChannelUseCase,
    private val stringsResource: StringsResource
) : BaseViewModel<CreateChannelUiState, CreateChannelUiEffect>(CreateChannelUiState()),
    CreateChannelInteraction {
    override fun onNextClicked() {
        tryToExecute(
            { manageChannelUseCase.validateChannelName(state.value.channelName) },
            ::onValidateNameSuccess,
            ::onError
        )
    }

    override fun onChannelNameTextChange(channelName: String) {
        _state.update { it.copy(channelName = channelName) }
    }

    override fun onChannelDescriptionChange(channelDescription: String?) {
        _state.update { it.copy(description = channelDescription.takeUnless { channelDescription.isNullOrEmpty() }) }
    }

    override fun onChannelStatusChange(newChannelStatus: ChannelStatus, isPrivate: Boolean) {
        _state.update { it.copy(status = newChannelStatus, isPrivate = isPrivate) }
    }

    private fun onValidateNameSuccess(channelName: String) {
        _state.update { it.copy(isLoading = false) }
        sendUiEffect(
            effect = CreateChannelUiEffect.NavigationToChooseMembers(
                channelName = _state.value.channelName,
                description = _state.value.description ?: "Welcome to $channelName",
                isPrivate = _state.value.isPrivate
            )
        )
    }

    private fun onError(throwable: Throwable) {
        val errorMessage = when (throwable) {
            is InvalidChannelNameException -> stringsResource.invalidChannelName
            is NoConnectionException -> stringsResource.noConnectionMessage
            else -> stringsResource.globalMessageError
        }
        _state.update { it.copy(message = errorMessage, isError = true, isLoading = false) }
    }

}