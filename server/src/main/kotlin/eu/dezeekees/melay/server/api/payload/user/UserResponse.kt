package eu.dezeekees.melay.server.api.payload.user

import eu.dezeekees.melay.common.result.UserResult
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class UserResponse(
    override val id: String? = null,
    override val username: String,
    override val displayName: String,
    override var profilePicture: String? = null,
    override var profileDescription: String? = null,
    override val createdAt: String? = null,
): UserResult