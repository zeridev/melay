package eu.dezeekees.melay.server.logic.dto.response

import eu.dezeekees.melay.common.result.TokenResult

data class TokenResponse(
    override val token: String,
): TokenResult
