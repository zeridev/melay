package eu.dezeekees.melay.server.data.mapper

import eu.dezeekees.melay.server.data.entity.UserCommunityMembershipEntity
import eu.dezeekees.melay.server.logic.model.UserCommunityMembership

object UserCommunityMembershipMapper {
    fun toModel(source: UserCommunityMembershipEntity) = UserCommunityMembership(
        userId = source.user.id.value,
        communityId = source.user.id.value,
        joinedAt = source.joinedAt
    )
}