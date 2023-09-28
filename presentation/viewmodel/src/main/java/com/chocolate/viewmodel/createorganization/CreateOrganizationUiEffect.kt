package com.chocolate.viewmodel.createorganization

import com.chocolate.viewmodel.base.BaseViewModel

sealed interface CreateOrganizationUiEffect : BaseViewModel.BaseUiEffect {
    data class NavigateToCreateMemberScreen(val role: String, val organizationImageUri: String) :
        CreateOrganizationUiEffect

    object NavigateToHaveOrganization : CreateOrganizationUiEffect
}
