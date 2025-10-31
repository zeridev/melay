package eu.dezeekees.melay.server.data.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


data class User(
    val id: String? = null,
    val username: String,
    var displayName: String,
    var passwordHash: String,
    var profilePicture: String? = null,
    var profileDescription: String? = null,
    val createdAt: LocalDateTime? = null,
)
