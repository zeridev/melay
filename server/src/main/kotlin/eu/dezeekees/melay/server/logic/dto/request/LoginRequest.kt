package eu.dezeekees.melay.server.presentation.dto.request

import eu.dezeekees.melay.server.logic.command.LoginCommand

data class LoginRequest(
    val username: String,
    val password: String
)

fun LoginRequest.toCommand(): LoginCommand = LoginCommand(username, password)
