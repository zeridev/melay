package eu.dezeekees.melay.server.api.mapper

import eu.dezeekees.melay.server.api.payload.message.CreateMessageRequest
import eu.dezeekees.melay.server.api.payload.message.MessageResponse
import eu.dezeekees.melay.server.api.payload.message.UpdateMessageRequest
import eu.dezeekees.melay.server.logic.exception.InternalServerErrorException
import eu.dezeekees.melay.server.logic.model.Message

object MessageMapper {

    fun toModel(request: CreateMessageRequest) = Message(
        channelId = request.channelId,
        content = request.content
    )

    fun toModel(request: UpdateMessageRequest) = Message(
        id = request.id,
        content = request.content
            ?: throw InternalServerErrorException("Message content is null"),
        channelId = null
    )

    fun toResponse(message: Message): MessageResponse =
        MessageResponse(
            id = message.id
                ?: throw InternalServerErrorException("Message id is null"),
            channelId = message.channelId
                ?: throw InternalServerErrorException("Message channelId is null"),
            author = UserMapper.toResponse(message.author ?: throw InternalServerErrorException("User is null")),
            content = message.content,
            createdAt = message.createdAt
                ?: throw InternalServerErrorException("Message createdAt is null"),
            editedAt = message.editedAt
        )
}
