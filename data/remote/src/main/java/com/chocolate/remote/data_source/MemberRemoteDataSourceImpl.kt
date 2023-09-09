package com.chocolate.remote.data_source

import com.chocolate.entities.channel.Channel
import com.chocolate.remote.util.Constants
import com.chocolate.remote.util.tryToExecuteSuspendCall
import com.chocolate.repository.datastore.remote.MemberRemoteDataSource
import com.chocolate.repository.model.dto.member.MemberDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
) : MemberRemoteDataSource {

    override suspend fun getMemberInOrganizationById(
        memberId: String,
        organizationName: String
    ): MemberDto? {
        return tryToExecuteSuspendCall {
            val memberRef = firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .document(organizationName)
                .collection(Constants.MEMBERS)
                .document(memberId)
                .get()
                .await()
            memberRef.toObject<MemberDto>()
        }
    }

    override suspend fun getMembersInChannelByChannelId(
        organizationName: String,
        channelId: String
    ): Flow<List<MemberDto>> {
        return callbackFlow {
            val organizationRef = firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .document(organizationName)
                .collection(Constants.MEMBERS)
                .whereArrayContains("channelsId", channelId)
                .addSnapshotListener { membersSnapshot, exception ->
                    exception?.let { close(it) }
                    membersSnapshot?.documents?.mapNotNull { it.toObject<MemberDto>() }?.let {
                        trySend(it)
                    }
                }
            awaitClose { organizationRef.remove() }
        }
    }

    override suspend fun getMembersInOrganizationByOrganizationName(organizationName: String): Flow<List<MemberDto>?> {
        return callbackFlow {
            val organizationRef = firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .document(organizationName)
                .collection(Constants.MEMBERS)
                .addSnapshotListener { membersSnapshot, exception ->
                    exception?.let { close(it) }
                    trySend(membersSnapshot?.documents?.mapNotNull { it.toObject<MemberDto>() })
                }
            awaitClose { organizationRef.remove() }
        }
    }

    override suspend fun deactivateMember(organizationName: String, memberId: String) {
        tryToExecuteSuspendCall {
            firebaseFirestore
                .collection(Constants.MEMBERS)
                .document(memberId)
                .update("isActive", false)
                .await()
        }
    }

    override suspend fun updateMember(organizationName: String, member: MemberDto) {
        tryToExecuteSuspendCall {
            firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .document(organizationName)
                .collection(Constants.MEMBERS)
                .document(member.id!!)
                .set(member)
                .await()
        }
    }

    override suspend fun changeRole(organizationName: String, memberId: String, newRole: String) {
        tryToExecuteSuspendCall {
            firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .document(organizationName)
                .collection(Constants.MEMBERS)
                .document(memberId)
                .update("role", newRole)
                .await()
        }
    }

    override suspend fun addMembersInChannel(
        organizationName: String,
        members: List<String>,
        channelId: String
    ) {
        tryToExecuteSuspendCall {
            val channelDocRef = firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .document(organizationName)
                .collection(Constants.CHANNEL)
                .document(channelId)
            val channelRef = channelDocRef
                .get()
                .await()
            channelRef.toObject<Channel>()?.let { channel ->
                val oldMembersId = channel.membersId.toMutableList()
                oldMembersId.addAll(members)
                val newMembersId = oldMembersId.toList()
                val newChannel = channel.copy(membersId = newMembersId)
                channelDocRef
                    .set(newChannel)
                    .await()
            }
        }
    }

    override suspend fun getMemberInOrganizationByEmail(
        organizationName: String,
        email: String
    ): MemberDto? {
        return tryToExecuteSuspendCall {
            val memberRef = firebaseFirestore
                .collection(Constants.ORGANIZATION)
                .document(organizationName)
                .collection(Constants.MEMBERS)
                .whereEqualTo("email", email)
                .get()
                .await()

            memberRef.documents.firstOrNull()?.toObject<MemberDto>()
        }
    }

}