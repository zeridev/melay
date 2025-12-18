package eu.dezeekees.melay.app.logic.model.auth

import eu.dezeekees.melay.common.payload.auth.TokenResult
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    override val token: String,
): TokenResult