package eu.dezeekees.melay.server.logic.service

import eu.dezeekees.melay.server.logic.model.Channel
import eu.dezeekees.melay.server.logic.repository.ChannelRepository
import java.util.UUID

class ChannelService(
    private val channelRepository: ChannelRepository,
) {

    suspend fun create(channel: Channel) =
        channelRepository.create(channel)

    suspend fun update(channel: Channel) =
        channelRepository.update(channel)

    suspend fun delete(channelId: UUID) =
        channelRepository.delete(channelId)
}