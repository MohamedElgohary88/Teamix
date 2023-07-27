package com.chocolate.remote.scheduled_message.response


import com.google.gson.annotations.SerializedName

data class BaseScheduledMessageResponse(
    @SerializedName("msg")
    val msg: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("scheduled_message_id")
    val scheduledMessageId: Int,
    @SerializedName("stream_id")
    val streamId: Int,
)