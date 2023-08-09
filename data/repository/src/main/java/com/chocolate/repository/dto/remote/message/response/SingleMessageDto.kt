package com.chocolate.repository.dto.remote.message.response

import com.google.gson.annotations.SerializedName

data class SingleMessageDto(
    @SerializedName("message")
    val message: MessageDto?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("raw_content")
    val rawContent: String?,
    @SerializedName("result")
    val result: String?,
    @SerializedName("code")
    val code: String?
)