package eu.dezeekees.melay.server.api.payload.channel

import eu.dezeekees.melay.server.logic.model.ChannelType
import kotlinx.serialization.Serializable

@Serializable
data class UpdateChannelRequest(
    val name: String?,
    val position: Int?,
    val type: ChannelType?
)
