package eu.dezeekees.melay.server.api.payload.auth

import eu.dezeekees.melay.common.command.RegisterUserCommand
import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserRequest(
    override val username: String,
    override val password: String,
): RegisterUserCommand