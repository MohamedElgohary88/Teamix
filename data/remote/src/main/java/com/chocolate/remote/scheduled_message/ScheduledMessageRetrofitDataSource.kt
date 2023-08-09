package com.chocolate.remote.scheduled_message

import com.chocolate.remote.scheduled_message.service.ScheduledMessageService
import com.chocolate.repository.model.dto.scheduled_message.response.BaseScheduledMessageResponse
import com.chocolate.repository.model.dto.scheduled_message.response.ScheduledMessagesDto
import com.chocolate.repository.service.remote.ScheduledMessageRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class ScheduledMessageRetrofitDataSource @Inject constructor(
    private val scheduledMessageService: ScheduledMessageService
): ScheduledMessageRemoteDataSource {
    override suspend fun getScheduledMessages(): Response<ScheduledMessagesDto> {
        return scheduledMessageService.getScheduledMessages()
    }

    override suspend fun createScheduledMessage(
        type: String,
        to: Any,
        content: String,
        topic: String,
        scheduledDeliveryTimestamp: Long
    ): Response<BaseScheduledMessageResponse> {
        return scheduledMessageService.createScheduledMessage(type, to, content, topic, scheduledDeliveryTimestamp)
    }

    override suspend fun editScheduledMessage(
        id: Int,
        type: String?,
        to: Any?,
        content: String?,
        topic: String?,
        scheduledDeliveryTimestamp: Long?
    ): Response<BaseScheduledMessageResponse> {
        return scheduledMessageService.editScheduledMessage(id, type, to, content, topic, scheduledDeliveryTimestamp)
    }

    override suspend fun deleteScheduledMessage(id: Int): Response<BaseScheduledMessageResponse> {
        return scheduledMessageService.deleteScheduledMessage(id)
    }
}