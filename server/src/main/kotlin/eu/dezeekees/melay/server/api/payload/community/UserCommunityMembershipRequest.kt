package eu.dezeekees.melay.server.api.payload.community

import eu.dezeekees.melay.common.payload.community.UserCommunityMembershipCommand
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserCommunityMembershipRequest(
    @Contextual override val userId: UUID?,
    @Contextual override val communityId: UUID,
): UserCommunityMembershipCommand