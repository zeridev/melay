package eu.dezeekees.melay.server.api.payload.community

import eu.dezeekees.melay.server.api.payload.channel.ChannelResponse
import kotlinx.datetime.Instant
import java.util.UUID

data class CommunityResponse(
    val id: UUID,
    val name: String,
    val description: String,
    val iconUrl: String,
    val bannerUrl: String,
    val createdAt: Instant,
    val channels: List<ChannelResponse>,
)
