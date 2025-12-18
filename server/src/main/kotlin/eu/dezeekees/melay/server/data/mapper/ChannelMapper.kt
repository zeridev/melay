package eu.dezeekees.melay.server.data.mapper

import eu.dezeekees.melay.server.data.entity.ChannelEntity
import eu.dezeekees.melay.server.logic.model.Channel

object ChannelMapper {

    fun toChannel(source: ChannelEntity) = Channel(
        id = source.id.value,
        name = source.name,
        type = source.type,
        position = source.position,
        createdAt = source.createdAt,
    )
}
