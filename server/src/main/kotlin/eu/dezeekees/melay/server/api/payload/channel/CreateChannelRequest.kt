package eu.dezeekees.melay.server.api.payload.channel

import eu.dezeekees.melay.server.logic.model.ChannelType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreateChannelRequest(
    val name: String,
    val position: Int,
    val type: ChannelType,
    @Contextual val communityId: UUID
)
