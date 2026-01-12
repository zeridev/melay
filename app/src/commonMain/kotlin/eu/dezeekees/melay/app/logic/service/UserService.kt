package eu.dezeekees.melay.app.logic.service

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.model.LocalUserData
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.model.user.UserResponse
import eu.dezeekees.melay.app.logic.repository.UserDataStoreRepository
import eu.dezeekees.melay.app.logic.repository.UserRepository

data class UserService(
    private val userRepository: UserRepository,
    private val userDataStoreRepository: UserDataStoreRepository
) {
    suspend fun getById(uuid: String): Result<UserResponse, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return userRepository.getById(uuid, userData.remoteDomain)
    }

    suspend fun getMe(): Result<UserResponse, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return userRepository.getMe(userData.remoteDomain)
    }

    suspend fun getMyCommunities(): Result<List<CommunityResponse>, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return userRepository.getMyCommunities(userData.remoteDomain)
    }
}
