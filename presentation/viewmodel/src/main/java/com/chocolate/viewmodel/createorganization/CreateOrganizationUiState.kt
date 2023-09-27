package com.chocolate.viewmodel.createorganization

import android.net.Uri
import com.chocolate.entities.entity.Organization
import com.chocolate.entities.util.Empty


data class CreateOrganizationUiState(
     val organizationName: String = String.Empty,
     val organizationImageUri: Uri? = null,
     val isLoading: Boolean = false,
     val error: String? = null,
     val showSnakeBar:Boolean=false
)

fun CreateOrganizationUiState.toEntity(): Organization {
    return Organization(
        name=this.organizationName,
        imageUrl=this.organizationImageUri.toString(),
        invitationCode="",
    )
}