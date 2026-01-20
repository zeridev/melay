package eu.dezeekees.melay.server.logic.service

import eu.dezeekees.melay.server.logic.model.Community
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserCommunityMembershipRepository
import java.util.UUID

class UserCommunityMembershipService(
    private val userCommunityMembershipRepository: UserCommunityMembershipRepository
) {
    suspend fun addMembership(userId: UUID, communityId: UUID) =
        userCommunityMembershipRepository.addMembership(userId, communityId)

    suspend fun removeMembership(userId: UUID, communityId: UUID) =
        userCommunityMembershipRepository.removeMembership(userId, communityId)

    suspend fun findCommunitiesForUser(userId: UUID): List<Community> =
        userCommunityMembershipRepository.findCommunitiesForUser(userId)

    suspend fun findUsersForCommunity(communityId: UUID): List<User> =
        userCommunityMembershipRepository.findUsersForCommunity(communityId)
}