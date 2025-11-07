package eu.dezeekees.melay.server.api.payload.auth

import eu.dezeekees.melay.common.result.TokenResult
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    override val token: String,
): TokenResult