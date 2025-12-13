package eu.dezeekees.melay.server.api.payload.community

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserCommunityMembershipRequest(
    @Contextual val userId: UUID,
    @Contextual val communityId: UUID,
)