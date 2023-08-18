package com.chocolate.viewmodel.organization_name

import androidx.lifecycle.viewModelScope
import com.chocolate.entities.exceptions.NoConnectionException
import com.chocolate.usecases.onboarding.GetOnboardingStateUseCase
import com.chocolate.usecases.organization.SaveNameOrganizationUseCase
import com.chocolate.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrganizationNameViewModel @Inject constructor(
    private val saveNameOrganizationsUseCase: SaveNameOrganizationUseCase,
    private val getOnboardingStateUseCase: GetOnboardingStateUseCase
) : BaseViewModel<OrganizationNameUiState, OrganizationNameUiEffect>(OrganizationNameUiState()),
    OrganizationNameInteraction {

    init {
        getOnboardingState()
    }

    override fun onClickCreateOrganization() {
        sendUiEffect(OrganizationNameUiEffect.NavigateToCreateOrganization)
    }

    override fun onClickActionButton(organizationName: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute({ saveNameOrganizationsUseCase(organizationName) }, ::onSuccess, ::onError)
    }

    private fun onSuccess(isCheck: Boolean) {
        _state.update { it.copy(isLoading = false, error = null) }
        if (isCheck) {
            sendUiEffect(OrganizationNameUiEffect.NavigateToLoginScreen)
        }
    }

    private fun onError(throwable: Throwable) {
        val errorMessage = when (throwable) {
            is NoConnectionException -> "No internet connection"
            else -> "Organization name cannot be empty"
        }
        _state.update { it.copy(isLoading = false, error = errorMessage) }
    }

    private fun getOnboardingState() {
        viewModelScope.launch {
            collectFlow(getOnboardingStateUseCase()) {
                this.copy(
                    onboardingState = it
                )
            }
        }
    }

    override fun onOrganizationNameChange(organizationName: String) {
        _state.update { it.copy(organizationName = organizationName.trim(), isLoading = false) }
    }
}