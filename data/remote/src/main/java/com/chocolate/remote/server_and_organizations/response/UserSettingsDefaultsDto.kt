package com.chocolate.remote.server_and_organizations.response

data class UserSettingsDefaultsDto(
    val ignored_parameters_unsupported: List<String>?,
    val msg: String,
    val result: String
)