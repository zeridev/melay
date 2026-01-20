package eu.dezeekees.melay.common.payload.auth

interface LoginCommand {
    val username: String
    val password: String
}