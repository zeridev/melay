package eu.dezeekees.melay.server.api.payload.message

import eu.dezeekees.melay.common.payload.message.MessageResult
import eu.dezeekees.melay.common.payload.user.UserResult
import eu.dezeekees.melay.server.api.payload.user.UserResponse
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MessageResponse(
    @Contextual override val id: UUID,
    @Contextual override val channelId: UUID,
    override val author: UserResponse,
    override val content: String,
    override val createdAt: Instant,
    override val editedAt: Instant?
): MessageResult
