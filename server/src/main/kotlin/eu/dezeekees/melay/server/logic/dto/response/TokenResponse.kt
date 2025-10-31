package eu.dezeekees.melay.server.presentation.dto.response

import eu.dezeekees.melay.server.logic.command.TokenResult

data class TokenResponse(
    val token: String,
)

fun TokenResult.toResponse(): TokenResponse = TokenResponse(token)
