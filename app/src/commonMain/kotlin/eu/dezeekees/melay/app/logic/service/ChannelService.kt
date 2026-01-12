package eu.dezeekees.melay.app.logic.service

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.model.LocalUserData
import eu.dezeekees.melay.app.logic.model.channel.ChannelResponse
import eu.dezeekees.melay.app.logic.model.channel.CreateChannelRequest
import eu.dezeekees.melay.app.logic.repository.ChannelRepository
import eu.dezeekees.melay.app.logic.repository.UserDataStoreRepository
import eu.dezeekees.melay.common.payload.ChannelType
import java.util.UUID

class ChannelService(
    private val channelRepository: ChannelRepository,
    private val userDataStoreRepository: UserDataStoreRepository
) {
    suspend fun createChannel(
        channelName: String,
        communityId: UUID,
    ): Result<ChannelResponse, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return channelRepository.createChannel(
            CreateChannelRequest(
                name = channelName,
                position = 0,
                type = ChannelType.TEXT,
                communityId = communityId
            ),
            userData.remoteDomain
        )
    }

    suspend fun deleteChannel(channelId: UUID): Result<Unit, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return channelRepository.deleteChannel(channelId, userData.remoteDomain)
    }
}