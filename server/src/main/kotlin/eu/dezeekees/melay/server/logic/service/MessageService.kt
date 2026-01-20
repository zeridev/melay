package eu.dezeekees.melay.server.logic.service

import eu.dezeekees.melay.server.logic.broadcast.MessageBroadcaster
import eu.dezeekees.melay.server.logic.model.Message
import eu.dezeekees.melay.server.logic.repository.MessageRepository
import java.util.UUID

class MessageService(
    private val messageRepository: MessageRepository,
    private val messageBroadcaster: MessageBroadcaster,
) {
    suspend fun getAllForChannel(channelId: UUID): List<Message> =
        messageRepository.getAllForChannel(channelId)

    suspend fun create(message: Message, userId: UUID): Message {
        val saved = messageRepository.create(message, userId)
        messageBroadcaster.broadcast(saved)
        return saved
    }

    suspend fun update(message: Message) =
        messageRepository.update(message)

    suspend fun delete(messageId: UUID) =
        messageRepository.delete(messageId)

    fun stream(channelId: UUID) = messageBroadcaster.stream(channelId)
}