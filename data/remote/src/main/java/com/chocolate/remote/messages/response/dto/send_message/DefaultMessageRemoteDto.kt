package com.chocolate.remote.messages.response.dto.send_message


import com.google.gson.annotations.SerializedName

data class DefaultMessageRemoteDto(
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("result")
    val result: String?,
    @SerializedName("code")
    val code: String?
)