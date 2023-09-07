package com.chocolate.repository.datastore.realtime

import com.chocolate.entities.messages.Topic
import com.chocolate.repository.datastore.realtime.model.TopicDto
import kotlinx.coroutines.flow.Flow

interface TopicDataSource {

    suspend fun createTopic(channelName:String,topic:TopicDto,organizationName:String)
    suspend fun getTopicsInAChannel(channelName: String,organizationName:String):Flow<List<Topic>>
    suspend fun getMessagesInATopic(channelId: String, topicId: String):Flow<TopicDto>
}