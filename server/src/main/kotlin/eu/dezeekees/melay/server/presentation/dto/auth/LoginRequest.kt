package eu.dezeekees.melay.server.presentation.dto.auth

import eu.dezeekees.melay.common.command.LoginCommand
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    override val username: String,
    @field:NotBlank
    override val password: String
): LoginCommand