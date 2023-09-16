package com.chocolate.repository.datastore.remote

import com.chocolate.entities.member.Member
import com.chocolate.repository.model.dto.member.MemberDto
import kotlinx.coroutines.flow.Flow

interface MemberRemoteDataSource {
    suspend fun getMemberInOrganizationById(memberId: String, organizationName: String): MemberDto?
    suspend fun getMembersInChannelByChannelId(
        organizationName: String,
        channelId: String
    ): Flow<List<MemberDto>>

    suspend fun getMembersInOrganizationByOrganizationName(organizationName: String): Flow<List<MemberDto>?>
    suspend fun deactivateMember(organizationName: String, memberId: String)
    suspend fun updateMember(organizationName: String, member: MemberDto)
    suspend fun changeRole(organizationName: String, memberId: String, newRole: String)
    suspend fun addMembersInChannel(
        organizationName: String,
        members: List<String>,
        channelId: String
    )

    suspend fun getMemberInOrganizationByEmail(organizationName: String, email: String): MemberDto?
    suspend fun updateMemberImage(organizationName: String, member: MemberDto)
}