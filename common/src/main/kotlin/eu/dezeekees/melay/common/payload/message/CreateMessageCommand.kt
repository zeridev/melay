package eu.dezeekees.melay.common.payload.message

import java.util.UUID

interface CreateMessageCommand {
    val channelId: UUID
    val content: String
}