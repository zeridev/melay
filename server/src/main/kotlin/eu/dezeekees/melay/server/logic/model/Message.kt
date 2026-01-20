package eu.dezeekees.melay.server.logic.model


import kotlinx.datetime.Instant
import java.util.UUID

data class Message(
    val id: UUID? = null,
    val channelId: UUID? = null,
    val author: User? = null,
    val content: String,
    val createdAt: Instant? = null,
    val editedAt: Instant? = null
)