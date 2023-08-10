package com.chocolate.repository.model.dto.users.request

import com.google.gson.annotations.SerializedName

data class TypingStatusDto(
    @SerializedName("op")
    val op: String,
    @SerializedName("to")
    val to: List<Int>,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("topic")
    val topic: String? = null
)