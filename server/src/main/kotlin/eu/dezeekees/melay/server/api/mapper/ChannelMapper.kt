package eu.dezeekees.melay.server.api.mapper

import eu.dezeekees.melay.server.api.payload.channel.ChannelResponse
import eu.dezeekees.melay.server.api.payload.channel.CreateChannelRequest
import eu.dezeekees.melay.server.api.payload.channel.UpdateChannelRequest
import eu.dezeekees.melay.server.logic.exception.InternalServerErrorException
import eu.dezeekees.melay.server.logic.model.Channel
import java.util.UUID

object ChannelMapper {

    fun toModel(createChannelRequest: CreateChannelRequest) = Channel(
        name = createChannelRequest.name,
        position = createChannelRequest.position,
        type = createChannelRequest.type,
        communityId = createChannelRequest.communityId,
    )

    fun toModel(updateChannelRequest: UpdateChannelRequest) = Channel(
        name = updateChannelRequest.name!!,
        position = updateChannelRequest.position!!,
        type = updateChannelRequest.type!!,
        communityId = null
    )

    fun toResponse(channel: Channel): ChannelResponse = ChannelResponse(
        id = channel.id ?: throw InternalServerErrorException("Channel id is null"),
        name = channel.name,
        type = channel.type,
        position = channel.position,
        createdAt = channel.createdAt ?: throw InternalServerErrorException("Channel createdAt is null"),
    )

}