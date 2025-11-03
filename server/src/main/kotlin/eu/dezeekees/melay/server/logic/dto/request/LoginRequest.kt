package eu.dezeekees.melay.server.logic.dto.request

import eu.dezeekees.melay.common.command.LoginCommand

data class LoginRequest(
    override val username: String,
    override val password: String
): LoginCommand
