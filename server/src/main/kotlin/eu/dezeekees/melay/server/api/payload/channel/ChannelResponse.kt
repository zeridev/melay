package eu.dezeekees.melay.server.api.payload.channel

import eu.dezeekees.melay.server.logic.model.ChannelType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ChannelResponse(
    @Contextual val id: UUID,
    val name: String,
    val type: ChannelType,
    val position: Int,
    val createdAt: kotlinx.datetime.Instant,
)
