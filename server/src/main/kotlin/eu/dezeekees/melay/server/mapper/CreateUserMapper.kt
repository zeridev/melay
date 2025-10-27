package eu.dezeekees.melay.server.mapper

import eu.dezeekees.melay.server.dto.request.CreateUserRequest
import eu.dezeekees.melay.server.model.User

object CreateUserMapper: BaseMapper<User, CreateUserRequest, Unit> {
    override fun fromRequest(request: CreateUserRequest): User =
        User(
            username = request.username,
            displayName = request.username,
            passwordHash = ""
        )

    override fun toResponse(entity: User): Unit = Unit
}