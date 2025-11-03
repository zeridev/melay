package eu.dezeekees.melay.server.logic.model

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID


data class User(
    val id: UUID? = null,
    val username: String,
    var displayName: String,
    var passwordHash: String,
    var profilePicture: String? = null,
    var profileDescription: String? = null,
    val createdAt: LocalDateTime? = null,
)
