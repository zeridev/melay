package eu.dezeekees.melay.common.payload.auth

interface RegisterUserCommand {
    val username: String
    val password: String
}