package com.chocolate.repository.mappers

import com.chocolate.entities.entity.Organization
import com.chocolate.repository.model.dto.OrganizationDto

@JvmName("organizationDtoToOrganization")
fun OrganizationDto.toEntity(): Organization = Organization(
    name = name!!,
    imageUrl = imageUrl!!,
    invitationCode = invitationCode!!,
)

@JvmName("organizationToOrganizationDto")
fun Organization.toRemote(): OrganizationDto = OrganizationDto(
    name = name,
    imageUrl = imageUrl,
    invitationCode = invitationCode,
)