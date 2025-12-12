package eu.dezeekees.melay.server.api.payload.channel

import eu.dezeekees.melay.server.logic.model.ChannelType

data class UpdateChannelRequest(
    val name: String?,
    val position: Int?,
    val type: ChannelType?
)
