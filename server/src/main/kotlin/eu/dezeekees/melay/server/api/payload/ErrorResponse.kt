package eu.dezeekees.melay.server.api.payload

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val reasons: List<String>,
)
