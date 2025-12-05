package eu.dezeekees.melay.server.api.rsocket.routing

import io.rsocket.kotlin.ExperimentalMetadataApi
import io.rsocket.kotlin.metadata.RoutingMetadata
import io.rsocket.kotlin.metadata.read
import io.rsocket.kotlin.payload.Payload

class PayloadRouteMatcher(val payload: Payload) {
    @OptIn(ExperimentalMetadataApi::class)
    private val incomingRouteString: String? =
        payload.metadata?.read(RoutingMetadata)?.tags?.firstOrNull()

    fun match(routePattern: String, block: (Map<String, String>) -> Unit) {
        val incomingRoute = incomingRouteString?.let { parseRoute(it) } ?: return
        val patternRoute = parseRoute(routePattern)

        if(incomingRoute.name == patternRoute.name) {
            block(incomingRoute.params)
        }
    }
}