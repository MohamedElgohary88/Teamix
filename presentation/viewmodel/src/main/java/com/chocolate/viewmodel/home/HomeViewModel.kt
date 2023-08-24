package com.chocolate.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.chocolate.entities.channel.Channel
import com.chocolate.entities.exceptions.NoConnectionException
import com.chocolate.entities.exceptions.UnAuthorizedException
import com.chocolate.entities.exceptions.ValidationException
import com.chocolate.usecases.channel.GetChannelsUseCase
import com.chocolate.usecases.channel.GetSubscribedChannelsUseCase
import com.chocolate.usecases.organization.GetImageOrganizationUseCase
import com.chocolate.usecases.organization.GetNameOrganizationsUseCase
import com.chocolate.usecases.user.GetUserLoginStatusUseCase
import com.chocolate.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserLoginStatusUseCase: GetUserLoginStatusUseCase,
    private val getSubscribedChannelsUseCase: GetSubscribedChannelsUseCase,
    private val getImageOrganizationUseCase: GetImageOrganizationUseCase,
    private val getNameOrganizationsUseCase: GetNameOrganizationsUseCase
) : BaseViewModel<HomeUiState, HomeUiEffect>(HomeUiState()), HomeInteraction {

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getUserLoginState()
            onGettingOrganizationName()
            onGettingOrganizationImage()
            onGettingChannels()
        }
    }

    private fun onGettingOrganizationImage() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getImageOrganizationUseCase() },
            ::onGettingOrganizationImageSuccess,
            ::onError
        )
    }

    private fun onGettingOrganizationImageSuccess(Image: String) {
        _state.update {
            it.copy(
                isLoading = true,
                imageUrl = Image,
                showNoInternetLottie = false,
                error = null
            )
        }
    }

    private fun onGettingOrganizationName() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { getNameOrganizationsUseCase() },
            ::onGettingOrganizationNameSuccess,
            ::onError
        )
    }

    private fun onGettingOrganizationNameSuccess(organizationName: String) {
        _state.update {
            it.copy(
                isLoading = true,
                organizationTitle = organizationName,
                showNoInternetLottie = false,
                error = null
            )
        }
    }

    private fun onGettingChannels() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute({ getSubscribedChannelsUseCase() }, ::onGettingChannelsSuccess, ::onError)
    }

    private fun onGettingChannelsSuccess(channels: List<Channel>) {
        _state.update { it.copy(isLoading = false, channels = channels.toUiState(), error = null) }
    }

    private fun getUserLoginState() {
        viewModelScope.launch {
            collectFlow(getUserLoginStatusUseCase()) {
                this.copy(
                    isLogged = it,
                    isLoading = false
                )
            }
        }
        //  tryToExecute({ getUserLoginStatusUseCase() }, ::onLoginStateSuccess, ::onLoginError)
    }

    private fun onLoginStateSuccess(userLoginStatus: Boolean) {
        _state.update { it.copy(isLoading = false, isLogged = userLoginStatus) }
    }

    private fun onLoginError(throwable: Throwable) {
        onError(throwable)
    }

    private fun onError(throwable: Throwable) {
        Log.d("home", throwable.toString())
        when (throwable) {
            is UnAuthorizedException, is ValidationException -> sendUiEffect(HomeUiEffect.NavigateToOrganizationName)
            is NoConnectionException -> _state.update {
                it.copy(
                    showNoInternetLottie = true,
                    isLoading = false
                )
            }
        }
        _state.update { it.copy(isLoading = false, error = throwable.message) }
    }

    override fun onClickDrafts() {
        sendUiEffect(HomeUiEffect.NavigationToDrafts)
    }

    override fun onClickStarred() {
        sendUiEffect(HomeUiEffect.NavigationToStarred)
    }

    override fun onClickSavedLater() {
        sendUiEffect(HomeUiEffect.NavigationToSavedLater)
    }

    override fun onClickChannel(id: Int) {
        sendUiEffect(HomeUiEffect.NavigateToChannel)
    }

    override fun onClickTopic(name: String) {
        sendUiEffect(HomeUiEffect.NavigateToTopic)
    }
}