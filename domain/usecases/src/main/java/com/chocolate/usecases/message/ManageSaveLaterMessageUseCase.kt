package com.chocolate.usecases.message

import com.chocolate.entities.messages.Message
import repositories.MessagesRepository
import javax.inject.Inject

class ManageSaveLaterMessageUseCase @Inject constructor(
    private val messagesRepository: MessagesRepository
) {
    suspend fun saveMassage(message: Message) = messagesRepository.saveMessage(message)

    suspend fun getSavedMessages(): List<Message> {
        val messages = messagesRepository.getSavedMessages().sortedByDescending { it.timestamp }
//       val groupedMessages = messages.groupBy { message ->
//            formatDate(message.timestamp)
//        }

        return messages
    }

    suspend fun deleteSavedMessageById(id: Int) = messagesRepository.deleteMessage(id)


}