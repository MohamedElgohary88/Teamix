package com.chocolate.repository.model.dto.users.response

import com.google.gson.annotations.SerializedName

data class UsersStateDto(
    @SerializedName("msg")
    val message: String?,
    @SerializedName("presences")
    val presencesDto: PresencesDto?,
    @SerializedName("result")
    val result: String?,
    @SerializedName("server_timestamp")
    val serverTimestamp: Double?
)