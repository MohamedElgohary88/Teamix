package com.chocolate.repository.model.dto

data class ChannelDto(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val membersId:List<String>? = null,
)