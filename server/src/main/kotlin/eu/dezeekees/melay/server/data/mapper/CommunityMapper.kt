package eu.dezeekees.melay.server.data.mapper

import eu.dezeekees.melay.server.data.entity.CommunityEntity
import eu.dezeekees.melay.server.logic.model.Community

object CommunityMapper {
    fun toCommunity(source: CommunityEntity) = Community(
        id = source.id.value,
        name = source.name,
        description = source.description,
        iconUrl = source.iconUrl ?: "",
        bannerUrl = source.bannerUrl ?: "",
        createdAt = source.createdAt,
        channels = source.channels.map { ChannelMapper.toChannel(it) }
    )
}
