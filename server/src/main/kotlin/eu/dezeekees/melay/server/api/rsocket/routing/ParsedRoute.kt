package eu.dezeekees.melay.server.api.rsocket.routing

data class ParsedRoute(
    val name: String,
    val params: Map<String, String>
)

