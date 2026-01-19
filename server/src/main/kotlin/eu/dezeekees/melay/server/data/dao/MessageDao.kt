package eu.dezeekees.melay.server.data.dao

import eu.dezeekees.melay.server.data.entity.ChannelEntity
import eu.dezeekees.melay.server.data.entity.MessageEntity
import eu.dezeekees.melay.server.data.entity.Messages
import eu.dezeekees.melay.server.data.entity.UserEntity
import eu.dezeekees.melay.server.data.entity.Users
import eu.dezeekees.melay.server.data.mapper.MessageMapper
import eu.dezeekees.melay.server.logic.exception.NotFoundException
import eu.dezeekees.melay.server.logic.model.Message
import eu.dezeekees.melay.server.logic.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class MessageDao : MessageRepository {

    override suspend fun getAllForChannel(channelId: UUID): List<Message> = withContext(Dispatchers.IO) {
        transaction {
            MessageEntity.find { Messages.channelId eq channelId }
                .orderBy(Messages.createdAt to SortOrder.ASC) // optional: oldest first
                .map { MessageMapper.toMessage(it) }
        }
    }

    override suspend fun create(message: Message, userId: UUID): Message = withContext(Dispatchers.IO) {
        transaction {
            val channelEntity = ChannelEntity.findById(message.channelId!!)
                ?: throw NotFoundException("Channel ID ${message.channelId} not found")

            val userEntity = UserEntity.findById(userId) ?: throw NotFoundException("User ID $userId not found")

            val entity = MessageEntity.new {
                channelId = channelEntity.id
                author = userEntity
                content = message.content
            }

            entity.flush()
            entity.refresh()

            MessageMapper.toMessage(entity)
        }
    }

    override suspend fun update(message: Message): Message = withContext(Dispatchers.IO) {
        val entity = MessageEntity.findByIdAndUpdate(message.id!!) { newMessage ->
            newMessage.content =
                message.content.takeIf { it.isNotBlank() } ?: newMessage.content
            newMessage.editedAt = Clock.System.now()
        }

        if (entity == null) {
            throw NotFoundException("Message with id ${message.id} not found")
        }

        entity.flush()
        entity.refresh()

        MessageMapper.toMessage(entity)
    }

    override suspend fun delete(messageId: UUID) = withContext(Dispatchers.IO) {
        transaction {
            Messages.deleteWhere { id eq messageId }
            return@transaction
        }
    }
}
