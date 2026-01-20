package eu.dezeekees.melay.app.logic.repository

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.model.message.CreateMessageRequest
import eu.dezeekees.melay.app.logic.model.message.MessageResponse
import java.util.UUID

interface MessageRepository {
    suspend fun getAllForChannel(channelId: UUID, domain: String): Result<List<MessageResponse>, Error>
    suspend fun createMessage(request: CreateMessageRequest, domain: String): Result<MessageResponse, Error>
    suspend fun deleteMessage(messageId: UUID, domain: String): Result<Unit, Error>
}