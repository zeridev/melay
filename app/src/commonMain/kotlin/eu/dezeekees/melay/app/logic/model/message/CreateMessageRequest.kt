package eu.dezeekees.melay.app.logic.model.message

import eu.dezeekees.melay.common.payload.message.CreateMessageCommand
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreateMessageRequest(
    @Contextual override val channelId: UUID,
    override val content: String
): CreateMessageCommand
