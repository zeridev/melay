package eu.dezeekees.melay.server.api.payload.community

import eu.dezeekees.melay.common.payload.community.CommunityResult
import eu.dezeekees.melay.server.api.payload.channel.ChannelResponse
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CommunityResponse(
    @Contextual override val id: UUID,
    override val name: String,
    override val description: String,
    override val iconUrl: String,
    override val bannerUrl: String,
    override val createdAt: Instant,
    override val channels: List<ChannelResponse>,
): CommunityResult
