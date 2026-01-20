package eu.dezeekees.melay.app.logic.model.message

import eu.dezeekees.melay.app.logic.model.user.UserResponse
import eu.dezeekees.melay.common.payload.message.MessageResult
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
