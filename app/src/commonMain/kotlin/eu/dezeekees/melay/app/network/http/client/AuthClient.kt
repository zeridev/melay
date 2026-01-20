package eu.dezeekees.melay.app.network.http.client

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.error.safeCall
import eu.dezeekees.melay.app.logic.model.auth.LoginRequest
import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.app.logic.repository.AuthRepository
import eu.dezeekees.melay.app.network.HttpClientProvider
import eu.dezeekees.melay.common.Routes
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthClient(
    private val clientProvider: HttpClientProvider
): AuthRepository {
    override suspend fun register() {
        TODO("Not yet implemented")
    }

    override suspend fun login(request: LoginRequest, domain: String): Result<Token, Error> = safeCall {
        clientProvider
            .get()
            .post("${domain}${Routes.Api.Auth.LOGIN}") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
    }
}