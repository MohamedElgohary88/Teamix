package com.chocolate.viewmodel.onboarding

import androidx.lifecycle.viewModelScope
import com.chocolate.usecases.onboarding.ManageUserUsedAppUseCase
import com.chocolate.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val manageUserUsedApp: ManageUserUsedAppUseCase,
) : BaseViewModel<OnboardingUiState, OnboardingUiEffect>(OnboardingUiState()),
    OnboardingInteraction {
    init {

    }



    override fun onClickLetsStart() {
        viewModelScope.launch {
            manageUserUsedApp.setUserUsedAppForFirstTime(true)
            sendUiEffect(OnboardingUiEffect.NavigateToOrganizationName)
        }
    }
}