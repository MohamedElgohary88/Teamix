package com.chocolate.repository.mappers.messages

import com.chocolate.entities.messages.SingleMessage
import com.chocolate.repository.dto.remote.message.response.SingleMessageDto

fun SingleMessageDto.toSingleMessage(): SingleMessage {
    return SingleMessage(
        message = this.message!!.toMessage(),
        rawContent = this.rawContent ?: ""
    )
}