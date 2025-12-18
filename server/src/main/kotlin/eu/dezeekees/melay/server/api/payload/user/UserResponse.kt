package eu.dezeekees.melay.server.api.payload.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String? = null,
    val username: String,
    val displayName: String,
    var profilePicture: String? = null,
    var profileDescription: String? = null,
    val createdAt: String? = null,
)