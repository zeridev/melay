package eu.dezeekees.melay.server.logic.repository

import eu.dezeekees.melay.server.logic.model.Community
import eu.dezeekees.melay.server.logic.model.User
import java.util.UUID

interface UserCommunityMembershipRepository {
    suspend fun addMembership(userId: UUID, communityId: UUID)
    suspend fun removeMembership(userId: UUID, communityId: UUID)
    suspend fun findCommunitiesForUser(userId: UUID): List<Community>
    suspend fun findUsersForCommunity(communityId: UUID): List<User>
}