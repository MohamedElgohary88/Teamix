package com.chocolate.repository.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.chocolate.entities.draft.Draft
import com.chocolate.entities.exceptions.EmptyMemberIdException
import com.chocolate.entities.exceptions.EmptyOrganizationNameException
import com.chocolate.entities.member.Member
import com.chocolate.entities.messages.Message
import com.chocolate.entities.messages.SavedLaterMessage
import com.chocolate.repository.datastore.local.PreferencesDataSource
import com.chocolate.repository.datastore.remote.MemberRemoteDataSource
import com.chocolate.repository.datastore.remote.MessagesRemoteDataSource
import com.chocolate.repository.datastore.remote.SavedLaterDataSource
import com.chocolate.repository.mappers.messages.toEntity
import com.chocolate.repository.mappers.messages.toMessage
import com.chocolate.repository.mappers.messages.toMessageDto
import com.chocolate.repository.mappers.messages.toRemote
import com.chocolate.repository.mappers.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import repositories.MessagesRepository
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(
    private val messagesRemoteDataSource: MessagesRemoteDataSource,
    private val preferencesDataSource: PreferencesDataSource,
    private val savedLaterDataSource: SavedLaterDataSource,
    private val memberDataSource: MemberRemoteDataSource
) : MessagesRepository {

    private suspend fun getCurrentOrganizationName(): String =
        preferencesDataSource.getCurrentOrganizationName()
            ?: throw EmptyOrganizationNameException

    private suspend fun getCurrentMember(): Member {
        val currentMemberId =
            preferencesDataSource.getIdOfCurrentMember() ?: throw EmptyMemberIdException
        return memberDataSource.getMemberInOrganizationById(
            currentMemberId,
            getCurrentOrganizationName()
        )?.toEntity() ?: throw EmptyMemberIdException
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun sendMessageInTopic(
        message: Message,
        topicId: String,
        channelId: String,
        organizationName: String
    ) {
        messagesRemoteDataSource.sendMessageInTopic(
            message = message.toMessageDto(),
            topicId = topicId,
            channelId = channelId,
            "teamixOrganization"
        )
    }

    override suspend fun getMessagesFromTopic(
        topicId: String,
        channelId: String,
        organizationName: String
    ): Flow<List<Message>> {
        return messagesRemoteDataSource.getMessagesFromTopic(
            topicId,
            channelId,
            "teamixOrganization"
        ).map { it.toMessage() }
    }

    override suspend fun getDrafts(): List<Draft> {
        TODO("Not yet implemented")
    }

    override suspend fun createDraft(
        type: String,
        recipients: Int,
        topic: String,
        content: String
    ): List<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDraft(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedLaterMessages(): Flow<List<SavedLaterMessage>> {
        return savedLaterDataSource.getSavedLaterMessages(
            getCurrentOrganizationName(),
            getCurrentMember().id
        ).toEntity(getCurrentMember())
    }

    override suspend fun saveMessage(message: SavedLaterMessage) {
        val member = memberDataSource.getMemberInOrganizationById(message.sender.id, getCurrentOrganizationName())
        val newMessage = message.copy(
            sender = member?.toEntity() ?: throw EmptyMemberIdException
        )
        savedLaterDataSource.addSavedLaterMessage(getCurrentOrganizationName(), newMessage.toRemote())
    }

    override suspend fun deleteSavedLaterMessageById(savedLaterMessageId: String) {
        savedLaterDataSource.deleteSavedLaterMessageById(
            getCurrentOrganizationName(),
            getCurrentMember().id,
            savedLaterMessageId
        )
    }
}