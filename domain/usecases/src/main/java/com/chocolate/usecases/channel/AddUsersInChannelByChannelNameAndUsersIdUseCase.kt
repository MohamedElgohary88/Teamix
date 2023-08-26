package com.chocolate.usecases.channel

import com.chocolate.entities.exceptions.ValidationException
import repositories.ChannelsRepository
import javax.inject.Inject

class AddUsersInChannelByChannelNameAndUsersIdUseCase @Inject constructor(
    private val repository: ChannelsRepository
) {

    suspend operator fun invoke(
        channelName: String,
        usersId: List<Int> = listOf(),
        description: String? = "",
        isPrivate: Boolean = false
    ): Boolean {
        if (isValidChannelName(channelName)) {
            createChannel(
                channelName = channelName,
                usersId = usersId,
                description = description,
                isPrivate = isPrivate
            )
            return true
        } else {
            throw ValidationException("Channel name must not be empty and should have fewer than 60 characters.")
        }
    }

    private fun isValidChannelName(channelName: String): Boolean {
        return channelName.isNotBlank() && channelName.length < 60
    }

    private suspend fun createChannel(
        channelName: String,
        usersId: List<Int>,
        description: String?,
        isPrivate: Boolean
    ): Boolean {
        return repository.subscribeToChannel(
            channelName = channelName,
            usersId = usersId,
            description = description,
            isPrivate = isPrivate
        )
    }
}