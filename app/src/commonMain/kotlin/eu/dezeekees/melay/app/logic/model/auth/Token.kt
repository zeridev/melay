package eu.dezeekees.melay.app.logic.model.auth

import eu.dezeekees.melay.common.result.TokenResult
import kotlinx.serialization.Serializable

@Serializable
data class Token(
    override val token: String
): TokenResult
