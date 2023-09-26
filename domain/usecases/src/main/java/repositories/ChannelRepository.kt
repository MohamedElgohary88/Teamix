package repositories

import com.chocolate.entities.channel.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    suspend fun createChannelInOrganization(
        channel: Channel,
        organizationName: String,
    )

    suspend fun getChannelsInOrganizationByOrganizationName(
        organizationName: String
    ): Flow<List<Channel>>

    suspend fun getChannelsForMemberByMemberIdInOrganization(
        currentMemberId: String,
        organizationName: String
    ): Flow<List<Channel>>
}