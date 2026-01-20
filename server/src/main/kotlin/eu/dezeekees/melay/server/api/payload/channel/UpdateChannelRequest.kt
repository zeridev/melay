package eu.dezeekees.melay.server.api.payload.channel

import eu.dezeekees.melay.common.payload.ChannelType
import kotlinx.serialization.Serializable

@Serializable
data class UpdateChannelRequest(
    val name: String?,
    val position: Int?,
    val type: ChannelType?
)
