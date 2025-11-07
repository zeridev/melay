package eu.dezeekees.melay.server.data

import eu.dezeekees.melay.server.data.entity.UserEntity
import eu.dezeekees.melay.server.logic.model.User

object UserMapper {
    fun toUser(source: UserEntity) = User(
        id = source.id,
        username = source.username,
        displayName = source.displayName,
        passwordHash = source.passwordHash,
        profilePicture = source.profilePicture,
        profileDescription = source.profileDescription,
        createdAt = source.createdAt
    )

    fun toEntity(source: User) = UserEntity(
        id = source.id,
        username = source.username,
        displayName = source.displayName,
        passwordHash = source.passwordHash,
        profilePicture = source.profilePicture,
        profileDescription = source.profileDescription,
        createdAt = source.createdAt
    )
}