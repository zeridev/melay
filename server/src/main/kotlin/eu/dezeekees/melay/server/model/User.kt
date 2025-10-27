package eu.dezeekees.melay.server.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("user")
data class User(
    val id: String? = null,
    val username: String,
    @Column("display_name") var displayName: String,
    @Column("password_hash") var passwordHash: String,
    @Column("profile_picture") var profilePicture: String? = null,
    @Column("profile_description") var profileDescription: String? = null,
    @Column("created_at") val createdAt: LocalDateTime? = null,
)
