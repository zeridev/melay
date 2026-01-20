import eu.dezeekees.melay.server.data.entity.CommunityEntity
import eu.dezeekees.melay.server.data.entity.UserCommunityMembershipEntity
import eu.dezeekees.melay.server.data.entity.UserCommunityMemberships
import eu.dezeekees.melay.server.data.entity.UserEntity
import eu.dezeekees.melay.server.data.mapper.CommunityMapper
import eu.dezeekees.melay.server.data.mapper.UserMapper
import eu.dezeekees.melay.server.logic.exception.NotFoundException
import eu.dezeekees.melay.server.logic.model.Community
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserCommunityMembershipRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserCommunityMembershipDao : UserCommunityMembershipRepository {

    override suspend fun addMembership(userId: UUID, communityId: UUID) = withContext(Dispatchers.IO) {
        transaction {
            val userId = UserEntity.findById(userId) ?: throw NotFoundException("User Not Found")
            val communityId = CommunityEntity.findById(communityId) ?: throw NotFoundException("Community Not Found")

            val compositeId = CompositeID {
                it[UserCommunityMemberships.userId] = userId.id
                it[UserCommunityMemberships.communityId] = communityId.id
            }

            UserCommunityMembershipEntity.new(compositeId) {}

            return@transaction
        }
    }

    override suspend fun removeMembership(userId: UUID, communityId: UUID) = withContext(Dispatchers.IO) {
        transaction {
            UserCommunityMemberships.deleteWhere {
                UserCommunityMemberships.userId eq userId
                UserCommunityMemberships.communityId eq communityId
            }

            return@transaction
        }
    }

    override suspend fun findCommunitiesForUser(userId: UUID): List<Community> = withContext(Dispatchers.IO) {
        transaction {
            UserCommunityMembershipEntity.find { UserCommunityMemberships.userId eq userId }
                .map { membership -> CommunityMapper.toCommunity(membership.community) }
        }
    }

    override suspend fun findUsersForCommunity(communityId: UUID): List<User> = withContext(Dispatchers.IO) {
        transaction {
            UserCommunityMembershipEntity.find { UserCommunityMemberships.communityId eq communityId }
                .map { membership -> UserMapper.toUser(membership.user) }
        }
    }
}
