package com.chocolate.repository.mappers.messages

import com.chocolate.entities.messages.SendMessage
import com.chocolate.repository.model.dto.message.response.SendMessageDto

fun SendMessageDto.toSendMessage(): SendMessage {
    return SendMessage(
        id = this.id ?: 0
    )
}