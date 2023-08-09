package com.chocolate.viewmodel.organization_name

import com.chocolate.viewmodel.base.BaseErrorUiState

data class OrganizationNameUiState(
    val nameOrganization: String ="",
    val isLoading: Boolean = false,
    val error: BaseErrorUiState = BaseErrorUiState()
    )