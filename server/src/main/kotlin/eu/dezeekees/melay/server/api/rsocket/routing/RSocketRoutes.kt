package eu.dezeekees.melay.server.api.rsocket.routing

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.common.rsocket.ConfiguredProtoBuf
import eu.dezeekees.melay.common.rsocket.encodeToPayload
import eu.dezeekees.melay.server.api.rsocket.handler.messageRSocketHandler
import eu.dezeekees.melay.server.data.entity.Messages.channelId
import eu.dezeekees.melay.server.logic.service.MessageService
import io.ktor.server.routing.*
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.ktor.server.rSocket
import io.rsocket.kotlin.payload.Payload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf
import org.koin.ktor.ext.inject
import kotlin.to

@OptIn(ExperimentalSerializationApi::class)
val proto: ProtoBuf = ConfiguredProtoBuf

@OptIn(ExperimentalSerializationApi::class)
fun Route.rsocketRoutes() {
    val messageService by inject<MessageService>()

    rSocket(Routes.Socket.MelayClient.CONNECTION_ROUTE) {
        RSocketRequestHandler {

            requestChannel { initial, flow ->
                val routeMatcher = initial.route()

                val handlers = mapOf<String, (ParsedRoute) -> Flow<Payload>>(
                    "stream.{channel_id}.messages" to { initialRoute -> messageRSocketHandler(initialRoute, messageService) }
                )

                val handlerEntry = handlers.entries.firstOrNull { routeMatcher.match(it.key) != null }
                    ?: throw IllegalArgumentException("No route matched")

                val parsedRoute = routeMatcher.match(handlerEntry.key)!!
                return@requestChannel handlerEntry.value(parsedRoute)
            }
        }
    }
}