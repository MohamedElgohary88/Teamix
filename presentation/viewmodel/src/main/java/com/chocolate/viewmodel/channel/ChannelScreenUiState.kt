package com.chocolate.viewmodel.channel

import com.chocolate.entities.topic.Topic
import com.chocolate.entities.uills.Empty

data class ChannelScreenUiState(
    val channelName: String = String.Empty,
    val channelId: String = String.Empty,
    val topics: List<TopicState> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class TopicState(
    val id: String = String.Empty,
    val creatorName: String = String.Empty,
    val creatorImage: String = String.Empty,
    val topicName: String = String.Empty,
    val topicCreationDate: String = String.Empty,
    val replayImages: List<Int> = emptyList(),
    val topicContent: String = String.Empty
)

fun List<Topic>.toUiState(): List<TopicState> =
    map {
        TopicState(
            id = it.topicId,
            topicName = it.content,
            creatorName = it.senderName,
            creatorImage = it.senderImage,
            topicCreationDate = it.sentTIme.toString().take(10),
            topicContent = it.content
        )
    }
