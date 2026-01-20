package eu.dezeekees.melay.server.api.payload.message

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UpdateMessageRequest(
    @Contextual val id: UUID,
    val content: String?
)
