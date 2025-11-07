package eu.dezeekees.melay.server.presentation.mapper

import eu.dezeekees.melay.server.logic.model.Token
import eu.dezeekees.melay.server.presentation.dto.auth.TokenResponse

object AuthMapper {
    fun toTokenResponse(source: Token) = TokenResponse(
        token = source.token,
    )
}