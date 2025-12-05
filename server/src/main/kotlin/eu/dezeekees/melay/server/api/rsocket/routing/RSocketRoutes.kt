package eu.dezeekees.melay.server.api.rsocket.routing

import eu.dezeekees.melay.common.Routes
import io.ktor.server.routing.Route
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.ktor.server.rSocket
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
fun Route.rsocketRoutes() {
    rSocket(Routes.Socket.MelayClient.CONNECTION_ROUTE) {
        val proto = ConfiguredProtoBuf

        RSocketRequestHandler {
            requestStream { payload ->
                payload.whenRoute {
                    match("stream.{community_id}.{channel_id}.messages") { params ->

                    }
                }

                error("No route")
            }
        }
    }
}