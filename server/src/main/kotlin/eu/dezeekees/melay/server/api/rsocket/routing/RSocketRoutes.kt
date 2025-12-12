package eu.dezeekees.melay.server.api.rsocket.routing

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.common.rsocket.ConfiguredProtoBuf
import eu.dezeekees.melay.common.rsocket.encodeToPayload
import io.ktor.server.routing.*
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.ktor.server.rSocket
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)
val proto: ProtoBuf = ConfiguredProtoBuf

@OptIn(ExperimentalSerializationApi::class)
fun Route.rsocketRoutes() {
    rSocket(Routes.Socket.MelayClient.CONNECTION_ROUTE) {

        RSocketRequestHandler {

            requestChannel { initial, flow ->
                val routeMatcher = initial.route()

                val messagesMatch = routeMatcher.match("stream.{community_id}.{channel_id}.messages")

                when {
                    messagesMatch != null -> {
                        println(messagesMatch.params)
                        return@requestChannel flowOf(proto.encodeToPayload("test"))
                    }

                    else -> throw IllegalArgumentException("No route matched")
                }
            }
        }
    }
}