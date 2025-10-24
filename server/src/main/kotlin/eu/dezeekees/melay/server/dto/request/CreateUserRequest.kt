package eu.dezeekees.melay.server.dto.request

data class CreateUserRequest(
    val username: String,
    val password: String,
)
