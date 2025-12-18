package eu.dezeekees.melay.app.network.http.client

import eu.dezeekees.melay.app.logic.repository.AuthRepository
import eu.dezeekees.melay.app.logic.error.NetworkError
import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.NetworkErrorType
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.model.auth.LoginRequest
import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.common.Routes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.network.*
import kotlinx.serialization.SerializationException

class AuthClient(
    private val client: HttpClient
): AuthRepository {
    override suspend fun register() {
        TODO("Not yet implemented")
    }

    override suspend fun login(request: LoginRequest, domain: String): Result<Token, Error> {
        val response = try {
            client.post("${domain}${Routes.Api.Auth.LOGIN}") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        } catch (e: Exception) {
            return when (e) {
                is UnresolvedAddressException -> Result.Error(NetworkError(type = NetworkErrorType.NO_INTERNET))
                is SerializationException -> Result.Error(NetworkError(type = NetworkErrorType.SERIALIZATION))
                else -> Result.Error(NetworkError())
            }
        }

        return when (response.status.value) {
            in 200..299 -> Result.Success(response.body<Token>())
            400 -> Result.Error(response.body<NetworkError>().copy(
                type = NetworkErrorType.BAD_REQUEST,
            ))
            401 -> Result.Error(NetworkError(type = NetworkErrorType.UNAUTHORIZED))
            else -> Result.Error(NetworkError())
        }
    }
}