package com.chocolate.viewmodel.profile

import com.chocolate.entities.exceptions.EmptyEmailException
import com.chocolate.entities.exceptions.EmptyFullNameException
import com.chocolate.entities.exceptions.SameUserDataException
import com.chocolate.entities.user.OwnerUser
import com.chocolate.entities.user.Settings
import com.chocolate.usecases.user.CustomizeProfileSettingsUseCase
import com.chocolate.usecases.user.GetCurrentUserDataUseCase
import com.chocolate.usecases.user.LogoutUseCase
import com.chocolate.usecases.user.UpdateUserInformationUseCase
import com.chocolate.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserDataUseCase: GetCurrentUserDataUseCase,
    private val updateUserInformationUseCase: UpdateUserInformationUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val customizeProfileSettingsUseCase: CustomizeProfileSettingsUseCase
) : BaseViewModel<ProfileUiState, ProfileEffect>(ProfileUiState()), ProfileInteraction {

    private var newUsername: String = ""
    private var newEmail: String = ""
    private var originalName: String = ""
    private var originalEmail: String = ""

    init {
        getOwnUser()
    }
    private fun getOwnUser() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute({ getCurrentUserDataUseCase() }, ::onGetOwnUserSuccess, ::onGetOwnUserError)
    }

    private fun onGetOwnUserSuccess(ownerUser: OwnerUser) {
        val ownerUserUi = ownerUser.toOwnerUserUiState()
        _state.update {
            it.copy(
                name = ownerUserUi.name,
                image = ownerUserUi.image,
                email = ownerUserUi.email,
                role = ownerUserUi.role,
                showNoInternetLottie = false,
                isLoading = false,
                error = null
            )
        }
    }

    private fun onGetOwnUserError(throwable: Throwable){
        _state.update { it.copy(isLoading = false,showNoInternetLottie = true) }
    }

    override fun updateLanguageDialogState(showDialog: Boolean) {
        _state.update {it.copy(showLanguageDialog = showDialog) }
    }

    override fun updateThemeDialogState(showDialog: Boolean) {
        _state.update { it.copy(showThemeDialog = showDialog) }
    }

    override fun updateLogoutDialogState(showDialog: Boolean) {
        _state.update { it.copy(showLogoutDialog = showDialog) }
    }

    override fun updateWarningDialog(showDialog: Boolean) {
        _state.update { it.copy(showWarningDialog = showDialog) }
    }

    override fun onClickOwnerPower() {
        sendUiEffect(ProfileEffect.NavigateToOwnerPower)
    }

    override fun onUsernameChange(username: String) {
        if (originalName.isEmpty()) {
            originalName = _state.value.name
        }
        newUsername = username
        _state.update { it.copy(name = username) }
    }

    override fun onEmailChange(email: String) {
        if (originalEmail.isEmpty()) {
            originalEmail = _state.value.email
        }
        newEmail = email
        _state.update { it.copy(email = email) }
    }

    override fun onUserInformationFocusChange() {
        _state.update { it.copy(showWarningDialog = false) }
        val settingsState = Settings(fullName = _state.value.name, email = _state.value.email)
        tryToExecute(
            { updateUserInformationUseCase(settingsState) },
            ::onUpdateUserInformationSuccess,
            ::onError
        )
    }
    override fun onClickRetryToUpdatePersonalInformation() {
        onUserInformationFocusChange()
    }

    override fun onClickRetryToGetPersonalInformation() {
        getOwnUser()
    }

    override fun areUserDataEqual(): Boolean {
        val currentState = _state.value
        return currentState.name == newUsername || currentState.email == newEmail
    }

    override fun onRevertChange() {
        val currentState = _state.value
        val updatedState = currentState.copy(
            name = if(originalName == "") _state.value.name else originalName,
            email = if(originalEmail == "") _state.value.email else originalEmail
        )
        _state.update { updatedState }
        updateWarningDialog(false)
    }

    private fun onUpdateUserInformationSuccess(unit: Unit) {
        _state.update { it.copy(isLoading = false, error = null, message = "success") }
    }

    override fun updateClearHistoryState(showDialog: Boolean) {
        _state.update { it.copy(showClearHistoryDialog = showDialog) }
    }

    private fun onError(throwable: Throwable) {
        val error = when (throwable) {
            EmptyEmailException -> "Email can't be empty"
            EmptyFullNameException -> "Full name can't be empty"
            SameUserDataException -> "User Information can't be the same"
            else -> throwable.localizedMessage
        }
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun onLogoutButtonClicked() {
        tryToExecute(
            call = { logoutUseCase() },
            onSuccess = ::onLogoutSuccess,
            onError = ::onLogoutFail
        )
    }

    private fun onLogoutSuccess(unit: Unit) = sendUiEffect(ProfileEffect.NavigateToLoginScreen)

    private fun onLogoutFail(throwable: Throwable) {
        _state.update { it.copy(error = throwable.message) }
    }

    fun updateAppLanguage(newLanguage: String) {
        tryToExecute(
            call = { customizeProfileSettingsUseCase.saveNewSelectedLanguage(newLanguage)},
            onSuccess = {},
            onError = ::onUpdateAppLanguageFail
        )
    }

    private fun onUpdateAppLanguageFail(throwable: Throwable) {
        _state.update { it.copy(error = throwable.message) }
    }

}