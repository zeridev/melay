package eu.dezeekees.melay.common.command

interface RegisterUserCommand {
    val username: String
    val password: String
}