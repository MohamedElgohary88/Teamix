package com.chocolate.repository.mappers

import com.chocolate.entities.entity.Member
import com.chocolate.entities.entity.SavedMessage
import com.chocolate.repository.model.dto.message.SavedLaterMessageDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<SavedLaterMessageDto>>.toEntity(member: Member): Flow<List<SavedMessage>> {
    return this.map { it.map { savedLaterMessageDto -> savedLaterMessageDto.toEntity(member) } }
}

fun SavedLaterMessageDto.toEntity(member: Member): SavedMessage {
    return SavedMessage(
        id = id!!,
        sender = member,
        messageContent = messageContent!!,
        date = date!!
    )
}

fun SavedMessage.toRemote(): SavedLaterMessageDto = SavedLaterMessageDto(
    id = id,
    senderId = sender.id,
    messageContent = messageContent,
    date = date
)