package eu.dezeekees.melay.server.api.payload.community

import eu.dezeekees.melay.server.api.payload.channel.ChannelResponse
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CommunityResponse(
    @Contextual val id: UUID,
    val name: String,
    val description: String,
    val iconUrl: String,
    val bannerUrl: String,
    val createdAt: kotlinx.datetime.Instant,
    val channels: List<ChannelResponse>,
)
