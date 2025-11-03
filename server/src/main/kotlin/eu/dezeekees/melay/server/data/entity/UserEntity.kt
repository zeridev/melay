package eu.dezeekees.melay.server.data.entity

import eu.dezeekees.melay.server.logic.model.User
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("user")
data class UserEntity(
    val id: UUID? = null,
    val username: String,
    @Column("display_name") var displayName: String,
    @Column("password_hash") var passwordHash: String,
    @Column("profile_picture") var profilePicture: String? = null,
    @Column("profile_description") var profileDescription: String? = null,
    @Column("created_at") val createdAt: LocalDateTime? = null,
)

fun UserEntity.toUser() = User(
    id = this.id,
    username = this.username,
    displayName = this.displayName,
    passwordHash = this.passwordHash,
    profilePicture = this.profilePicture,
    profileDescription = this.profileDescription,
    createdAt = this.createdAt
)

fun User.toEntity() = UserEntity(
    id = this.id,
    username = this.username,
    displayName = this.displayName,
    passwordHash = this.passwordHash,
    profilePicture = this.profilePicture,
    profileDescription = this.profileDescription,
    createdAt = this.createdAt
)
