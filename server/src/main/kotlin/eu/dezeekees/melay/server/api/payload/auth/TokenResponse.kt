package eu.dezeekees.melay.server.api.payload.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val token: String,
)