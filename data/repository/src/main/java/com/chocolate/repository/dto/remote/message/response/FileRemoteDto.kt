package com.chocolate.repository.dto.remote.message.response

import com.google.gson.annotations.SerializedName

data class FileRemoteDto(
    @SerializedName("msg")
    val message: String?,
    @SerializedName("result")
    val result: String?,
    @SerializedName("uri")
    val uri: String?
)