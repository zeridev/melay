package eu.dezeekees.melay.common.payload.community

import eu.dezeekees.melay.common.payload.channel.ChannelResult
import kotlinx.datetime.Instant
import java.util.UUID

interface CommunityResult {
    val id: UUID
    val name: String
    val description: String
    val iconUrl: String
    val bannerUrl: String
    val createdAt: Instant
    val channels: List<ChannelResult>
}