package com.chocolate.repository.dto.remote.channels.response

import com.google.gson.annotations.SerializedName

data class SubscribeToStreamDto(

    @field:SerializedName("msg")
    val message: String? = null,
    @field:SerializedName("result")
    val result: String? = null,
    @field:SerializedName("subscribed")
    val subscribed: com.chocolate.repository.dto.remote.channels.response.Subscribed? = null,
    @field:SerializedName("already_subscribed")
    val alreadySubscribed: com.chocolate.repository.dto.remote.channels.response.AlreadySubscribed? = null,
    @field:SerializedName("unauthorized")
    val unauthorized:  List<String?>? = null
)