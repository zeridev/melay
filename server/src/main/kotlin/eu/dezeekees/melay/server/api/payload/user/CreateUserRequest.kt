package eu.dezeekees.melay.server.api.payload.user

import eu.dezeekees.melay.common.command.CreateUserCommand
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
//    @field:Size(min = 3, max = 32, message = "Name must be between 3 and 32 characters.")
//    @field:NotBlank(message = "Username is required")
//    @field:Pattern(
//        regexp = "^[a-z0-9_]+$",
//        message = "Username can only contain lowercase letters, numbers, and underscores"
//    )
    override val username: String,

//    @field:NotBlank(message = "Password is required")
//    @field:Size(min = 6, message = "Password must have at least 6 characters")
    override val password: String,
): CreateUserCommand