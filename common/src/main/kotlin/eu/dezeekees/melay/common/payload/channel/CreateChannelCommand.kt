package eu.dezeekees.melay.common.payload.channel

import eu.dezeekees.melay.common.payload.ChannelType
import java.util.UUID

interface CreateChannelCommand {
    val name: String
    val position: Int
    val type: ChannelType
    val communityId: UUID
}