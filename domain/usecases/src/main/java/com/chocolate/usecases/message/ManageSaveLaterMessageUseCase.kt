package com.chocolate.usecases.message

import com.chocolate.entities.messages.Message
import repositories.MessagesRepository
import javax.inject.Inject

class ManageSaveLaterMessageUseCase @Inject constructor(
    private val messagesRepository: MessagesRepository
) {
    suspend fun saveMassage(message: Message) = messagesRepository.saveMessage(message)

    suspend fun getSavedMessages()=messagesRepository.getSavedMessages()


    suspend fun deleteSavedMessageById(id: Int) = messagesRepository.deleteMessage(id)

}