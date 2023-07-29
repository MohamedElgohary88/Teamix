package com.chocolate.repository.dto.message.response


import com.google.gson.annotations.SerializedName

data class RenderMessageRemoteDto(
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("rendered")
    val rendered: String?,
    @SerializedName("result")
    val result: String?
)