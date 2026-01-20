package eu.dezeekees.melay.server.logic.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import java.util.UUID

data class UserCommunityMembership(
    val userId: UUID,
    val communityId: UUID,
    val joinedAt: LocalDateTime
)