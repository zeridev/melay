package eu.dezeekees.melay.app.logic.model.community

import eu.dezeekees.melay.app.logic.model.channel.ChannelResponse
import eu.dezeekees.melay.common.payload.community.CommunityResult
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CommunityResponse(
    @Contextual override val id: UUID,
    override val name: String = "",
    override val description: String = "",
    override val iconUrl: String = "",
    override val bannerUrl: String = "",
    override val createdAt: Instant = Instant.fromEpochMilliseconds(-1L),
    override val channels: List<ChannelResponse> = emptyList(),
): CommunityResult