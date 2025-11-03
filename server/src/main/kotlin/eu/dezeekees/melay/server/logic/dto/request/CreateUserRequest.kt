package eu.dezeekees.melay.server.logic.dto.request

import eu.dezeekees.melay.common.command.CreateUserCommand
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class CreateUserRequest(
    @field:Size(min = 3, max = 32, message = "Name must be between 3 and 32 characters.")
    @field:NotBlank(message = "Username is required")
    @field:Pattern(
        regexp = "^[a-z0-9_]+$",
        message = "Username can only contain lowercase letters, numbers, and underscores"
    )
    override val username: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must have at least 6 characters")
    override val password: String,
): CreateUserCommand
