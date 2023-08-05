package repositories.channels

import com.chocolate.entities.ChannelModels.ChannelDetails
import com.chocolate.entities.ChannelModels.ChannelId
import com.chocolate.entities.ChannelModels.ChannelSubscribers
import com.chocolate.entities.ChannelModels.StreamItem
import com.chocolate.entities.ChannelModels.SubscribeToStream
import com.chocolate.entities.ChannelModels.SubscriptionSettingsUpdate
import com.chocolate.entities.ChannelModels.SubscriptionStatus
import com.chocolate.entities.ChannelModels.DefaultChannelModel
import com.chocolate.entities.ChannelModels.Topics

interface ChannelsRepository {
    suspend fun getUserSubscriptions(): List<StreamItem?>?
    suspend fun addSubscribes(
        subscribeToStream: String,
        principals: List<String>?,
        authorizationErrorsFatal: Boolean,
        announce: Boolean,
        inviteOnly: Boolean,
        isWebPublic: Boolean,
        historyPublicToSubscribers: Boolean,
        streamPostPolicy: Int?,
        messageRetentionDays: String?,
        canRemoveSubscribersGroupId: Int?
    ): SubscribeToStream?

    suspend fun deleteSubscriber(
        subscriptions: String,
        principals: List<String>?
    ): DefaultChannelModel

    suspend fun getSubscriptionStatus(
        userId: Int,
        streamId: Int
    ): SubscriptionStatus

    suspend fun getAllSubscriber(streamId: Int): ChannelSubscribers
    suspend fun updateSubscriptionSettings(subscriptionData: String): SubscriptionSettingsUpdate?
    suspend fun getAllChannels(
        includePublic: Boolean,
        includeWebPublic: Boolean,
        includeSubscribed: Boolean,
        includeAllActive: Boolean,
        includeDefault: Boolean,
        includeOwnerSubscribed: Boolean
    ): List<StreamItem>?

    suspend fun getChannelById(streamId: Int): ChannelDetails?
    suspend fun getChannelId(channel: String): ChannelId?
    suspend fun updateChannel(
        streamId: Int,
        description: String?,
        newName: String?,
        isPrivate: Boolean?,
        isWebPublic: Boolean?,
        historyPublicToSubscribers: Boolean?,
        streamPostPolicy: Int?,
        messageRetentionDays: String?,
        canRemoveSubscribersGroupId: Int?
    ): DefaultChannelModel?

    suspend fun archiveChannel(channelId: Int): DefaultChannelModel?
    suspend fun getTopicsInChannel(channelId: Int): Topics?
    suspend fun setTopicMuting(
        topic: String,
        status: String,
        streamId: Int?,
        stream: String?
    ): DefaultChannelModel?

    suspend fun updatePersonalPreferenceTopic(
        streamId: Int,
        topic: String,
        visibilityPolicy: Int
    ): DefaultChannelModel?

    suspend fun deleteTopic(
        channelId: Int,
        topicName: String
    ): DefaultChannelModel?

    suspend fun addDefaultChannel(channelId: Int): DefaultChannelModel?
    suspend fun deleteDefaultChannel(channelId: Int): DefaultChannelModel?
}