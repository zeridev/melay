package eu.dezeekees.melay.server.api.payload.channel

import eu.dezeekees.melay.server.logic.model.ChannelType
import kotlinx.datetime.Instant
import java.util.UUID

data class ChannelResponse(
    val id: UUID,
    val name: String,
    val type: ChannelType,
    val position: Int,
    val createdAt: Instant,
)
