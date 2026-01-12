package eu.dezeekees.melay.app.logic.repository

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.model.channel.ChannelResponse
import eu.dezeekees.melay.app.logic.model.channel.CreateChannelRequest
import java.util.UUID

interface ChannelRepository {
    suspend fun createChannel(request: CreateChannelRequest, domain: String): Result<ChannelResponse, Error>
    suspend fun deleteChannel(id: UUID, domain: String): Result<Unit, Error>
}