package eu.dezeekees.melay.server.api.rsocket.handler

import eu.dezeekees.melay.common.rsocket.encodeToPayload
import eu.dezeekees.melay.server.api.mapper.MessageMapper
import eu.dezeekees.melay.server.api.rsocket.routing.ParsedRoute
import eu.dezeekees.melay.server.api.rsocket.routing.proto
import eu.dezeekees.melay.server.logic.service.MessageService
import io.rsocket.kotlin.payload.Payload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
fun messageRSocketHandler(
    route: ParsedRoute,
    messageService: MessageService
): Flow<Payload> {
    val channelId = runCatching { UUID.fromString(route.params["channel_id"]) }.getOrNull()
        ?: return emptyFlow()

    return messageService.stream(channelId)
        .map { message ->
            val response = MessageMapper.toResponse(message)
            proto.encodeToPayload(response)
        }
}