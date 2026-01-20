package eu.dezeekees.melay.app.network.http.client

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.error.safeCall
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.model.user.UserResponse
import eu.dezeekees.melay.app.logic.repository.UserRepository
import eu.dezeekees.melay.app.network.HttpClientProvider
import eu.dezeekees.melay.common.Routes
import io.ktor.client.call.*
import io.ktor.client.request.*

class UserClient(
    private val clientProvider: HttpClientProvider,
): UserRepository {
    override suspend fun getById(userId: String, domain: String): Result<UserResponse, Error> = safeCall {
        clientProvider
            .get()
            .get("${domain}${Routes.Api.User.NAME}/$userId")
            .body()
    }

    override suspend fun getMe(domain: String): Result<UserResponse, Error> = safeCall {
        clientProvider
            .get()
            .get("${domain}${Routes.Api.User.ME}")
            .body()
    }

    override suspend fun getMyCommunities(domain: String): Result<List<CommunityResponse>, Error> = safeCall {
        clientProvider
            .get()
            .get("${domain}${Routes.Api.User.COMMUNITIES}")
            .body()
    }
}