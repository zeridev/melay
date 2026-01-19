package eu.dezeekees.melay.server.data.mapper

import eu.dezeekees.melay.server.data.entity.MessageEntity
import eu.dezeekees.melay.server.logic.model.Message

object MessageMapper {

    fun toMessage(entity: MessageEntity): Message =
        Message(
            id = entity.id.value,
            channelId = entity.channelId.value,
            author = UserMapper.toUser(entity.author),
            content = entity.content,
            createdAt = entity.createdAt,
            editedAt = entity.editedAt
        )
}
