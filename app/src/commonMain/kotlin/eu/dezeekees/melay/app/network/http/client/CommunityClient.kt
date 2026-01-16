package eu.dezeekees.melay.app.network.http.client

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.error.safeCall
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.model.community.CreateCommunityRequest
import eu.dezeekees.melay.app.logic.model.community.UpdateCommunityRequest
import eu.dezeekees.melay.app.logic.model.community.UserCommunityMembershipRequest
import eu.dezeekees.melay.app.logic.repository.CommunityRepository
import eu.dezeekees.melay.app.network.HttpClientProvider
import eu.dezeekees.melay.common.Routes
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.util.UUID

class CommunityClient(
    private val clientProvider: HttpClientProvider,
): CommunityRepository {
    override suspend fun getAllCommunities(domain: String): Result<List<CommunityResponse>, Error> = safeCall {
        clientProvider
            .get()
            .get("${domain}${Routes.Api.Community.NAME}")
            .body()
    }

    override suspend fun createCommunity(
        request: CreateCommunityRequest,
        domain: String
    ): Result<CommunityResponse, Error> = safeCall {
        clientProvider
            .get()
            .post("${domain}${Routes.Api.Community.NAME}") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
    }

    override suspend fun deleteCommunity(
        id: UUID,
        domain: String
    ): Result<Unit, Error> = safeCall {
        clientProvider
            .get()
            .delete("${domain}${Routes.Api.Community.NAME}/${id}")
    }

    override suspend fun updateCommunity(
        request: UpdateCommunityRequest,
        domain: String
    ): Result<CommunityResponse, Error> = safeCall {
        clientProvider
            .get()
            .patch("${domain}${Routes.Api.Community.NAME}") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            .body()
    }

    override suspend fun joinCommunity(
        id: UUID,
        domain: String
    ): Result<Unit, Error> = safeCall {
        clientProvider
            .get()
            .post("${domain}${Routes.Api.Community.MEMBERS}") {
                contentType(ContentType.Application.Json)
                setBody(UserCommunityMembershipRequest(
                    userId = null,
                    communityId = id,
                ))
            }
    }

    override suspend fun leaveCommunity(
        id: UUID,
        domain: String
    ): Result<Unit, Error> = safeCall {
        clientProvider
            .get()
            .delete("${domain}${Routes.Api.Community.MEMBERS}/${id}")
    }
}