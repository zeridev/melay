package eu.dezeekees.melay.server.api.rsocket.routing

import io.rsocket.kotlin.ExperimentalMetadataApi
import io.rsocket.kotlin.metadata.RoutingMetadata
import io.rsocket.kotlin.metadata.read
import io.rsocket.kotlin.payload.Payload

class PayloadRouteMatcher(private val routeString: String?) {

    fun match(routePattern: String): ParsedRoute? {
        val incomingRoute = routeString?.let { parseRoute(it) } ?: return null
        val patternRoute = parseRoute(routePattern)

        return if (incomingRoute.name == patternRoute.name) {
            incomingRoute
        } else {
            null
        }
    }

    fun parseRoute(route: String): ParsedRoute {
        val paramRegex = Regex("""\{([^:{}]+):([^:{}]+)\}""")
        val nameSegments = mutableListOf<String>()
        val params = mutableMapOf<String, String>()

        val segments = route.split(".")

        for (segment in segments) {
            if(paramRegex.matches(segment)) {
                val bare = segment.removePrefix("{").removeSuffix("}")
                val parts = bare.split(":")
                val key = parts[0]
                val value = parts.getOrNull(1)

                nameSegments.add(key)
                if(!value.isNullOrBlank()) {
                    params[key] = value
                }
            } else {
                val bare = segment.removePrefix("{").removeSuffix("}")
                nameSegments.add(bare)
            }
        }

        return ParsedRoute(
            name = nameSegments.joinToString("."),
            params = params,
        )
    }

}

@OptIn(ExperimentalMetadataApi::class)
fun Payload.route(): PayloadRouteMatcher =
    PayloadRouteMatcher(metadata?.read(RoutingMetadata)?.tags?.firstOrNull())