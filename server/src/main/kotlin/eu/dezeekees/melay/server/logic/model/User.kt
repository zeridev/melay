package eu.dezeekees.melay.server.logic.model


import kotlinx.datetime.Instant
import java.util.*

data class User(
    val id: UUID? = null,
    val username: String,
    var displayName: String,
    var passwordHash: String,
    var profilePicture: String = "",
    var profileDescription: String = "",
    val createdAt: Instant? = null,
)
