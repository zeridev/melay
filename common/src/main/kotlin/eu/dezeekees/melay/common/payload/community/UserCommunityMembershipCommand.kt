package eu.dezeekees.melay.common.payload.community

import java.util.UUID

interface UserCommunityMembershipCommand {
    val userId: UUID?
    val communityId: UUID
}