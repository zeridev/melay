package eu.dezeekees.melay.server.logic.repository

import eu.dezeekees.melay.server.logic.model.Community
import java.util.UUID

interface CommunityRepository {
    suspend fun findById(id: UUID): Community
    suspend fun create(community: Community): Community
    suspend fun delete(communityId: UUID)
    suspend fun update(community: Community): Community
}