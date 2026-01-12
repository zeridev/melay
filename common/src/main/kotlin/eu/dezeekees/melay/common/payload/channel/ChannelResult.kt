package eu.dezeekees.melay.common.payload.channel

import eu.dezeekees.melay.common.payload.ChannelType
import kotlinx.datetime.Instant
import java.util.UUID

interface ChannelResult {
    val id: UUID
    val name: String
    val type: ChannelType
    val position: Int
    val createdAt: Instant
}