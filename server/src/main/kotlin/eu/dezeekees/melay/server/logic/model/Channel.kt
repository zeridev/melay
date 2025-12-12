package eu.dezeekees.melay.server.logic.model

import kotlinx.datetime.Instant
import java.util.UUID

data class Channel(
    val id: UUID? = null,
    val name: String,
    val type: ChannelType,
    var position: Int = 0,
    val createdAt: Instant? = null,
)