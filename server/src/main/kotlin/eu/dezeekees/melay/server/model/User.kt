package eu.dezeekees.melay.server.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("user")
data class User(
    val id: String? = null,
    val username: String,
    @Column("display_name") val displayName: String,
    @Column("password_hash") val passwordHash: String,
    @Column("profile_picture") val profilePicture: String? = null,
    @Column("profile_description") val profileDescription: String? = null,
    @Column("created_at") val createdAt: Long? = null,
)
