package eu.dezeekees.melay.server.logic.service

import eu.dezeekees.melay.server.logic.model.Community
import eu.dezeekees.melay.server.logic.repository.CommunityRepository
import java.util.UUID

class CommunityService(
    private val communityRepository: CommunityRepository,
) {
    suspend fun findAll(): List<Community> =
        communityRepository.findAll()

    suspend fun findById(uuid: UUID): Community =
        communityRepository.findById(uuid)

    suspend fun create(community: Community): Community =
        communityRepository.create(community)

    suspend fun update(community: Community): Community =
         communityRepository.update(community)

    suspend fun delete(communityId: UUID) =
        communityRepository.delete(communityId)
}