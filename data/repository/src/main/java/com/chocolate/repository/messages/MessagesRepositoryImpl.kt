package com.chocolate.repository.messages

import com.chocolate.entities.messages.NarrowItemEntity
import com.chocolate.remote.messages.service.MessageService
import repositories.messages.MessagesRepository
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(
    private val messageService: MessageService
): MessagesRepository {
    override suspend fun sendStreamMessage(
        type: String,
        to: String,
        topic: String?,
        content: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun sendDirectMessage(type: String, to: String, content: String) {
        TODO("Not yet implemented")
    }

    override suspend fun uploadFile(filePath: String) {
        TODO("Not yet implemented")
    }

    override suspend fun editMessage(
        message_id: Int,
        content: String,
        topic: String?,
        propagateMode: String?,
        sendNotificationToOldThread: Boolean,
        sendNotificationToNewThread: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(message_id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getMessages(
        anchor: String?,
        includeAnchor: Boolean,
        numBefore: Int,
        numAfter: Int,
        narrow: List<String>?,
        clientGravatar: Boolean,
        applyMarkdown: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun addEmojiReaction(
        messageId: Int,
        emojiName: String,
        emojiCode: String?,
        reactionType: String?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEmojiReaction(
        messageId: Int,
        emojiName: String,
        emojiCode: String?,
        reactionType: String?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun renderMessage(content: String) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSingleMessage(messageId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun checkIfMessagesMatchNarrow(
        msgIds: List<Int>,
        narrow: List<NarrowItemEntity>
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getMessagesEditHistory(messageId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMessageFlags(messages: List<Int>, op: String, flag: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePersonalMessageFlagsForNarrow(
        anchor: String,
        numBefore: Int,
        numAfter: Int,
        narrow: List<Map<String, String>>,
        op: String,
        flag: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun markAllMessagesAsRead() {
        TODO("Not yet implemented")
    }

    override suspend fun markStreamAsRead(steamId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun markTopicAsRead(steamId: Int, topicName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getMessageReadReceipts(messageId: Int) {
        TODO("Not yet implemented")
    }

}