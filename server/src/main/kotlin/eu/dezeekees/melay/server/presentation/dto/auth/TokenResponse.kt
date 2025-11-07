package eu.dezeekees.melay.server.presentation.dto.auth

import eu.dezeekees.melay.common.result.TokenResult

data class TokenResponse(
    override val token: String,
): TokenResult