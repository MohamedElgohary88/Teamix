package com.chocolate.entities.messages


import java.util.Date

data class Topic(
    val content: String,
    val messages: List<Message>,
    val senderName: String,
    val senderId: String,
    val senderImageUrl: String,
    val sentTime: Date,
)
