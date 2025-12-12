package eu.dezeekees.melay.server.data.dao

import eu.dezeekees.melay.server.data.entity.Communities
import eu.dezeekees.melay.server.data.entity.CommunityEntity
import eu.dezeekees.melay.server.data.mapper.CommunityMapper
import eu.dezeekees.melay.server.logic.exception.NotFoundException
import eu.dezeekees.melay.server.logic.model.Community
import eu.dezeekees.melay.server.logic.repository.CommunityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class CommunityDao : CommunityRepository {
    override suspend fun findById(id: UUID): Community = withContext(Dispatchers.IO) {
        transaction {
            val entity = CommunityEntity.findById(id) ?: throw NotFoundException("Community with id $id not found")
            CommunityMapper.toCommunity(entity)
        }
    }

    override suspend fun create(community: Community): Community = withContext(Dispatchers.IO) {
        transaction {
            val entity = CommunityEntity.new {
                name = community.name
                description = community.description
                iconUrl = ""
                bannerUrl = ""
            }

            entity.flush()
            entity.refresh()

            CommunityMapper.toCommunity(entity)
        }
    }

    override suspend fun delete(communityId: UUID) = withContext(Dispatchers.IO) {
        transaction {
            Communities.deleteWhere { Communities.id eq communityId }

            return@transaction
        }
    }

    override suspend fun update(community: Community): Community = withContext(Dispatchers.IO) {
        transaction {
            val entity = CommunityEntity.findByIdAndUpdate(community.id!!) { newCommunity ->
                newCommunity.name = community.name.takeIf { it.isNotBlank() } ?: newCommunity.name
                newCommunity.description = community.description.takeIf { it.isNotBlank() } ?: newCommunity.description
                newCommunity.iconUrl = community.iconUrl.takeIf { it.isNotBlank() } ?: newCommunity.iconUrl
                newCommunity.bannerUrl = community.bannerUrl.takeIf { it.isNotBlank() } ?: newCommunity.bannerUrl
            }

            if (entity == null) {
                throw NotFoundException("Community ${community.id} not found")
            }

            entity.flush()
            entity.refresh()

            CommunityMapper.toCommunity(entity)
        }
    }
}
