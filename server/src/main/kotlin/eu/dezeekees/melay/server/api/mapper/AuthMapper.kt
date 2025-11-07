package eu.dezeekees.melay.server.api.mapper

import eu.dezeekees.melay.server.logic.model.Token
import eu.dezeekees.melay.server.api.payload.auth.TokenResponse

object AuthMapper {
    fun toTokenResponse(source: Token) = TokenResponse(
        token = source.token,
    )
}