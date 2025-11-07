package eu.dezeekees.melay.server.presentation.mapper

import eu.dezeekees.melay.server.presentation.dto.user.CreateUserRequest
import eu.dezeekees.melay.server.presentation.dto.user.UserResponse
import eu.dezeekees.melay.server.logic.model.User

object UserMapper {
    fun toResponse(source: User) = UserResponse(
        id = source.id,
        username = source.username,
        displayName = source.displayName,
        profilePicture = source.profilePicture,
        profileDescription = source.profileDescription,
        createdAt = source.createdAt,
    )
}