package com.chocolate.repository.mappers.messages

import com.chocolate.entities.messages.Message
import com.chocolate.entities.messages.Reaction
import com.chocolate.repository.datastore.realtime.model.MessageDto
import com.chocolate.repository.model.dto.message.response.ReactionDto
import java.util.Date


@JvmName("ReactionDto")
fun List<ReactionDto>?.toEntity(): List<Reaction> =
    this?.map { Reaction(emojiCode = it.emoji_code, emojiName = it.emoji_name) }.orEmpty()

//fun SavedMessageLocalDto.toEntity(): Message{
//    return Message(
//        id = this.id,
//        senderAvatarUrl = this.senderImageUrl,
//        senderId = this.senderId,
//     //   senderEmail = "",
//        senderFullName = this.senderName,
//     //   reactions = emptyList(),
//        messageContent = this.messageContent,
//        topicId = 0,
//      //  senderFullName = this.senderName,
//     //   topic = "",
//     //   timestamp = this.date
//
//    )
//}

//fun Message.toLocalDto(): SavedMessageLocalDto{
//    return SavedMessageLocalDto(
//        id = this.id,
//        senderId = this.senderId,
//        senderName =" this.senderFullName",
//        senderImageUrl = "this.senderAvatarUrl",
//        messageContent = this.messageContent,
//        date =Timestamp.valueOf("sds")
//
//    )
//
//}

fun MessageDto.toMessage() = Message(
    id = id ?: "",
    senderId = userId ?: "",
    messageContent = content ?: "",
    senderFullName = senderName ?: "",
    senderAvatarUrl = senderImage ?: "",
    timestamp = timestamp ?: Date()
)

@JvmName("Message")
fun List<MessageDto>?.toMessage(): List<Message> = this?.map { it.toMessage() }.orEmpty()

fun Message.toMessageDto() = MessageDto(
    id = id,
    userId = senderId,
    content = messageContent,
    senderName = senderFullName,
    senderImage = senderAvatarUrl,
    timestamp = timestamp
)

@JvmName("MessageDto")
fun List<Message>?.toMessageDto(): List<MessageDto> = this?.map { it.toMessageDto() }.orEmpty()

