package eu.dezeekees.melay.server.api.mapper

import eu.dezeekees.melay.server.api.payload.user.UserResponse
import eu.dezeekees.melay.server.logic.model.User

object UserMapper {
    fun toResponse(source: User) = UserResponse(
        id = source.id.toString(),
        username = source.username,
        displayName = source.displayName,
        profilePicture = source.profilePicture,
        profileDescription = source.profileDescription,
        createdAt = source.createdAt.toString(),
    )
}