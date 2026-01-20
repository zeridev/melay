package eu.dezeekees.melay.app.logic.repository

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.model.user.UserResponse

interface UserRepository {
    suspend fun getById(userId: String, domain: String): Result<UserResponse, Error>
    suspend fun getMe(domain: String): Result<UserResponse, Error>
    suspend fun getMyCommunities(domain: String): Result<List<CommunityResponse>, Error>
}