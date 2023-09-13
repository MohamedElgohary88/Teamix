package com.chocolate.remote.data_source

import com.chocolate.entities.exceptions.TeamixException
import com.chocolate.remote.api.DraftService
import com.chocolate.remote.api.MessageService
import com.chocolate.remote.util.Constants
import com.chocolate.remote.util.getRandomId
import com.chocolate.remote.util.tryToExecuteSuspendCall
import com.chocolate.remote.wrapApiCall
import com.chocolate.repository.datastore.realtime.model.MessageDto
import com.chocolate.repository.datastore.remote.MessagesRemoteDataSource
import com.chocolate.repository.model.dto.draft.response.BaseDraftResponse
import com.chocolate.repository.model.dto.draft.response.DraftsDto
import com.chocolate.repository.model.dto.message.response.DefaultMessageRemoteDto
import com.chocolate.repository.model.dto.message.response.FileRemoteDto
import com.chocolate.repository.model.dto.message.response.MatchNarrowDto
import com.chocolate.repository.model.dto.message.response.MessageEditHistoryDto
import com.chocolate.repository.model.dto.message.response.MessageReadReceiptsDto
import com.chocolate.repository.model.dto.message.response.RenderMessageDto
import com.chocolate.repository.model.dto.message.response.SendMessageDto
import com.chocolate.repository.model.dto.message.response.SingleMessageDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import okhttp3.MultipartBody
import javax.inject.Inject

class MessagesFireBaseDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : MessagesRemoteDataSource {
    override suspend fun sendMessageInTopic(
        message: MessageDto,
        topicId: String,
        channelId: String,
        organizationName: String,
    ) {
        val messageId = getRandomId()
        val messageDto = MessageDto(
            id = messageId.toString(),
            content = message.content,
            userId = message.userId,
            senderName = message.senderName,
            senderImage = message.senderImage,
            timestamp = message.timestamp,
        )
        tryToExecuteSuspendCall {
            firebaseFirestore.collection(Constants.BASE)
                .document(organizationName)
                .collection(Constants.CHANNEL)
                .document(channelId)
                .collection(Constants.TOPICS)
                .document(topicId)
                .collection(Constants.MESSAGE)
                .document(messageId.toString())
                .set(messageDto)
                .await()
        }
    }

    override suspend fun getMessagesFromTopic(
        topicId: String,
        channelId: String,
        organizationName: String
    ): Flow<List<MessageDto>> {
        return callbackFlow {
            val listener = firebaseFirestore
                .collection(Constants.BASE)
                .document(organizationName)
                .collection(Constants.CHANNEL)
                .document(channelId)
                .collection(Constants.TOPICS)
                .document(topicId)
                .collection(Constants.MESSAGE).addSnapshotListener { value, error ->
                    if (error != null)
                        throw TeamixException(error.message)
                    val messages = value?.toObjects<MessageDto>()
                    messages?.let { trySend(it) }
                }
            awaitClose { listener.remove() }
        }
    }

}