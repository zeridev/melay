package eu.dezeekees.melay.server.logic.mapper

import eu.dezeekees.melay.server.logic.dto.request.CreateUserRequest
import eu.dezeekees.melay.server.logic.dto.response.UserResponse
import eu.dezeekees.melay.server.logic.model.User

fun CreateUserRequest.toUser() = User(
    username = username,
    passwordHash = password,
    displayName = username
)

fun User.toResponse() = UserResponse(
    id = this.id,
    username = this.username,
    displayName = this.displayName,
    profilePicture = this.profilePicture,
    profileDescription = this.profileDescription,
    createdAt = this.createdAt,
)