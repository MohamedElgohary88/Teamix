package com.chocolate.entities.entity

import java.util.Date

data class Topic(
    val topicId: String,
    val content: String,
    val senderName: String,
    val senderImage: String,
    val sentTIme: Date
)