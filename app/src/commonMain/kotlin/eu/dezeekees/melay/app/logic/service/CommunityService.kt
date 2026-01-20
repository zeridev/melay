package eu.dezeekees.melay.app.logic.service

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.model.LocalUserData
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.model.community.CreateCommunityRequest
import eu.dezeekees.melay.app.logic.model.community.UpdateCommunityRequest
import eu.dezeekees.melay.app.logic.repository.CommunityRepository
import eu.dezeekees.melay.app.logic.repository.UserDataStoreRepository
import java.util.UUID

class CommunityService(
    private val communityRepository: CommunityRepository,
    private val userDataStoreRepository: UserDataStoreRepository
) {
    suspend fun getAllCommunities(): Result<List<CommunityResponse>, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return communityRepository.getAllCommunities(userData.remoteDomain)
    }

    suspend fun createCommunity(name: String, description: String): Result<CommunityResponse, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return communityRepository.createCommunity(
            CreateCommunityRequest(
                name = name,
                description = description,
            ),
            userData.remoteDomain
        )
    }

    suspend fun deleteCommunity(id: UUID): Result<Unit, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return communityRepository.deleteCommunity(
            id,
            userData.remoteDomain
        )
    }

    suspend fun updateCommunity(id: UUID, name: String, description: String): Result<CommunityResponse, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return communityRepository.updateCommunity(
            UpdateCommunityRequest(
                id,
                name,
                description,
            ),
            userData.remoteDomain
        )
    }

    suspend fun joinCommunity(id: UUID): Result<Unit, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return communityRepository.joinCommunity(
            id,
            userData.remoteDomain
        )
    }

    suspend fun leaveCommunity(id: UUID): Result<Unit, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return communityRepository.leaveCommunity(
            id,
            userData.remoteDomain
        )
    }
}