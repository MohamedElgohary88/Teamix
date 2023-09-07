package repositories

import com.chocolate.entities.channel.Channel
import com.chocolate.entities.messages.Topic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {
    suspend fun createTopic(channelName: String, topicContent: String, senderId: String)
    suspend fun getTopicsInAChannel(channelId: String):Flow<List<Topic>>


}