package com.chocolate.repository.mappers.channel_mappers

import com.chocolate.entities.channel.Channel
import com.chocolate.entities.channel.Topic
import com.chocolate.repository.model.dto.channels.response.DefaultStreamDto
import com.chocolate.repository.model.dto.channels.response.StreamDto
import com.chocolate.repository.model.dto.channels.response.SubscribedStreamDto
import com.chocolate.repository.model.dto.channels.response.SubscriptionsItemDto
import com.chocolate.repository.model.dto.channels.response.TopicsInStreamDto
import com.chocolate.repository.model.dto.channels.response.TopicsItemDto

fun SubscriptionsItemDto.toEntity(
    topics: List<Topic>,
): Channel {
    return Channel(
        channelId = streamId ?: 0,
        channelName = name ?: "",
        description = description ?: "",
        invitationONly = inviteOnly ?: false,
        topics = topics,
        isCurrentUserSubscribed = true,
        isMuted = isMuted ?: false
    )
}

suspend fun SubscribedStreamDto.toEntity(getTopics: suspend (channelId: Int) -> List<Topic>): List<Channel> {
    return this.subscriptions?.map { it.toEntity(getTopics(it.streamId ?: 0)) } ?: emptyList()
}

fun List<StreamDto>?.toEntity(): List<Channel> =
    this?.map { it.toEntity() } ?: emptyList()

fun StreamDto.toEntity(): Channel {
    return Channel(
        channelId = this.streamId ?: 0,
        channelName = this.name ?: "",
        description = this.description ?: "",
        invitationONly = this.inviteOnly ?: false,
        topics = emptyList(),
        isCurrentUserSubscribed = false,
        false
    )
}

fun DefaultStreamDto.toSuccessOrFail(): Boolean = this.result?.equals("success") ?: false

fun TopicsItemDto.toEntity(): Topic = Topic(name ?: "", maxId ?: 0)

fun TopicsInStreamDto.toEntity(): List<Topic> = topics?.map { it.toEntity() } ?: emptyList()