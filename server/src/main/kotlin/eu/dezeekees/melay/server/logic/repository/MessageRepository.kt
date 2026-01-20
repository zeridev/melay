package eu.dezeekees.melay.server.logic.repository

import eu.dezeekees.melay.server.logic.model.Message
import java.util.UUID

interface MessageRepository {
    suspend fun getAllForChannel(channelId: UUID): List<Message>
    suspend fun create(message: Message, userId: UUID): Message
    suspend fun update(message: Message): Message
    suspend fun delete(messageId: UUID)
}