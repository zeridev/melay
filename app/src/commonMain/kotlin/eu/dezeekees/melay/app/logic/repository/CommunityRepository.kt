package eu.dezeekees.melay.app.logic.repository

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.model.community.CreateCommunityRequest
import eu.dezeekees.melay.app.logic.model.community.UpdateCommunityRequest
import java.util.UUID

interface CommunityRepository {
    suspend fun getAllCommunities(domain: String): Result<List<CommunityResponse>, Error>
    suspend fun createCommunity(request: CreateCommunityRequest, domain: String): Result<CommunityResponse, Error>
    suspend fun deleteCommunity(id: UUID, domain: String): Result<Unit, Error>
    suspend fun updateCommunity(request: UpdateCommunityRequest, domain: String): Result<CommunityResponse, Error>
    suspend fun joinCommunity(id: UUID, domain: String): Result<Unit, Error>
    suspend fun leaveCommunity(id: UUID, domain: String): Result<Unit, Error>
}