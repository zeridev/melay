package eu.dezeekees.melay.server.logic.repository

import eu.dezeekees.melay.server.logic.model.Channel
import java.util.UUID

interface ChannelRepository {
    suspend fun create(channel: Channel): Channel
    suspend fun update(channel: Channel): Channel
    suspend fun delete(channelId: UUID)
}