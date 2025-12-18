package eu.dezeekees.melay.app.logic.model.auth

import eu.dezeekees.melay.common.payload.auth.LoginCommand
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    override val username: String,
    override val password: String
) : LoginCommand
