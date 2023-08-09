package com.chocolate.remote.users

import com.chocolate.remote.users.service.UsersService
import com.chocolate.repository.model.dto.users.request.ProfileDataDto
import com.chocolate.repository.model.dto.users.request.SettingsDto
import com.chocolate.repository.model.dto.users.response.AlertWordsDto
import com.chocolate.repository.model.dto.users.response.CreateUserDto
import com.chocolate.repository.model.dto.users.response.FetchApiKeyDto
import com.chocolate.repository.model.dto.users.response.MuteUserResponseDto
import com.chocolate.repository.model.dto.users.response.OwnerUserDto
import com.chocolate.repository.model.dto.users.response.ResponseStateDto
import com.chocolate.repository.model.dto.users.response.SubgroupsOfUserGroupDto
import com.chocolate.repository.model.dto.users.response.UserAttachmentsDto
import com.chocolate.repository.model.dto.users.response.UserDto
import com.chocolate.repository.model.dto.users.response.UserGroupMembershipsDto
import com.chocolate.repository.model.dto.users.response.UserGroupsDto
import com.chocolate.repository.model.dto.users.response.UserMembershipStateDto
import com.chocolate.repository.model.dto.users.response.UserSettingsDto
import com.chocolate.repository.model.dto.users.response.UserStateDto
import com.chocolate.repository.model.dto.users.response.UsersDto
import com.chocolate.repository.model.dto.users.response.UsersStateDto
import com.chocolate.repository.service.remote.UsersRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

    class UsersRetrofitDataSource @Inject constructor(
        private val userService: UsersService
    ) : UsersRemoteDataSource {
        override suspend fun getAllUsers(
            clientGravatar: Boolean,
            includeCustomProfileFields: Boolean
        ) = userService.getAllUsers(clientGravatar, includeCustomProfileFields)

        override suspend fun getOwnUser() = userService.getOwnUser()

        override suspend fun getUserById(
            userId: Int,
            clientGravatar: Boolean,
            includeCustomProfileFields: Boolean
        ) = userService.getUserById(userId, clientGravatar, includeCustomProfileFields)

        override suspend fun getUserByEmail(
            email: String,
            clientGravatar: Boolean,
            includeCustomProfileFields: Boolean
        ) = userService.getUserByEmail(email, clientGravatar, includeCustomProfileFields)

        override suspend fun updateUserById(
            id: Int,
            fullName: String?,
            role: Int?,
            profileData: List<ProfileDataDto>?
        ) = userService.updateUserById(id, fullName, role, profileData)

        override suspend fun updateUserStatus(
            statusText: String?,
            away: Boolean?,
            emojiName: String?,
            emojiCode: String?,
            reactionType: String?
        ) = userService.updateUserStatus(statusText, away, emojiName, emojiCode, reactionType)

        override suspend fun createUser(
            email: String,
            password: String,
            fullName: String
        ) = userService.createUser(email, password, fullName)

        override suspend fun deactivateUserAccount(id: Int) = userService.deactivateUser(id)

        override suspend fun reactivateUserAccount(id: Int) = userService.reactivateUser(id)

        override suspend fun deactivateOwnUserAccount() = userService.deactivateOwnUser()

        override suspend fun setTypingStatus(
            op: String,
            to: String,
            type: String?,
            topic: String?
        ) = userService.setTypingStatus(op, to, type, topic)

        override suspend fun getUserPresence(email: String) = userService.getUserPresence(email)
        override suspend fun getRealmPresence() = userService.getRealmPresence()

        override suspend fun getAttachments() = userService.getAttachments()
        override suspend fun deleteAttachment(attachmentId: Int) =
            userService.deleteAttachment(attachmentId)

        override suspend fun updateSettings(settings: SettingsDto) =
            userService.updateSettings(settings)

        override suspend fun getUserGroups() = userService.getUserGroups()

        override suspend fun createUserGroup(
            name: String,
            description: String,
            members: String
        ) = userService.createUserGroup(name, description, members)

        override suspend fun updateUserGroup(
            userGroupId: Int,
            name: String,
            description: String
        ) = userService.updateUserGroup(userGroupId, name, description)

        override suspend fun removeUserGroup(userGroupId: Int) =
            userService.removeUserGroup(userGroupId)

        override suspend fun updateUserGroupMembers(
            id: Int,
            add: List<Int>,
            delete: List<Int>
        ) = userService.updateUserGroupMembers(id, add, delete)

        override suspend fun updateUserGroupSubgroups(
            userGroupId: Int,
            add: List<Int>?,
            delete: List<Int>?
        ) = userService.updateUserGroupSubgroups(userGroupId, add, delete)

        override suspend fun getUserMembership(
            groupId: Int,
            userId: Int,
            directMemberOnly: Boolean
        ) = userService.getUserMembership(groupId, userId, directMemberOnly)

        override suspend fun getUserGroupMemberships(
            groupId: Int,
            directMemberOnly: Boolean
        ) = userService.getUserGroupMemberships(groupId, directMemberOnly)

        override suspend fun getSubgroupsOfUserGroup(
            id: Int,
            directSubgroupOnly: Boolean
        ) = userService.getSubgroupsOfUserGroup(id, directSubgroupOnly)

        override suspend fun getAlertWords() = userService.getAlertWords()

        override suspend fun addAlertWords(alertWords: String) = userService.addAlertWords(alertWords)
        override suspend fun removeAlertWords(alertWords: String) =
            userService.removeAlertWords(alertWords)

        override suspend fun muteUser(mutedUserId: Int) = userService.muteUser(mutedUserId)

        override suspend fun unmuteUser(mutedUserId: Int) = userService.unmuteUser(mutedUserId)

        override suspend fun fetchApiKey(userName: String, password: String): Response<FetchApiKeyDto> {
            return userService.fetchApiKey(userName, password)
        }

    }