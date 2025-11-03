package eu.dezeekees.melay.common.command

interface CreateUserCommand {
    val username: String
    val password: String
}