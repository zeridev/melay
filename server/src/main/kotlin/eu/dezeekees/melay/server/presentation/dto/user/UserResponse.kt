package eu.dezeekees.melay.server.presentation.dto.user

import eu.dezeekees.melay.common.result.UserResult
import java.time.LocalDateTime
import java.util.UUID

data class UserResponse(
    override val id: UUID? = null,
    override val username: String,
    override val displayName: String,
    override var profilePicture: String? = null,
    override var profileDescription: String? = null,
    override val createdAt: LocalDateTime? = null,
): UserResult