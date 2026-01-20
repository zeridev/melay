package eu.dezeekees.melay.app.network

import eu.dezeekees.melay.app.logic.error.ApiException
import eu.dezeekees.melay.app.logic.error.NetworkError
import eu.dezeekees.melay.app.logic.error.NetworkErrorType
import eu.dezeekees.melay.app.logic.error.NetworkException
import eu.dezeekees.melay.app.logic.serializer.NetworkErrorSerializer
import eu.dezeekees.melay.app.logic.service.TokenService
import eu.dezeekees.melay.common.kotlinx.UUIDSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.rsocket.kotlin.ktor.client.RSocketSupport
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.util.UUID
import io.ktor.http.isSuccess
import kotlinx.serialization.SerializationException
import java.nio.channels.UnresolvedAddressException

expect fun createHttpClientEngine(): HttpClientEngine

class HttpClientProvider(
    private val tokenService: TokenService,
) {
    private var client: HttpClient? = null

    fun get(): HttpClient {
        if(client == null) {
            client = createHttpClient()
        }

        return client!!
    }

    fun invalidate() {
        client?.close()
        client = null
    }

    private fun createHttpClient(): HttpClient {
        return HttpClient(createHttpClientEngine()) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            install(WebSockets)
            install(RSocketSupport)
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        serializersModule = SerializersModule {
                            contextual(NetworkError::class, NetworkErrorSerializer)
                            contextual(UUID::class, UUIDSerializer)
                        }
                    }
                )
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val token = tokenService.getToken() ?: return@loadTokens null
                        BearerTokens(token.token, "")
                    }
                }
            }

            HttpResponseValidator {
                validateResponse { response ->
//                    if (!response.status.isSuccess()) {
//
//                        if (response.status == HttpStatusCode.Unauthorized) {
//                            tokenService.deleteToken()
//                        }
//
//                        throw ApiException.HttpError(
//                            status = response.status,
//                            response = response.takeIf { response.status.value == 400 }
//                        )
//                    }

                    when (response.status) {
                        HttpStatusCode.Unauthorized -> {
                            tokenService.deleteToken()
                            throw ApiException.HttpError(status = response.status)
                        }
                    }
                }

                handleResponseExceptionWithRequest { cause, _ ->
                    throw when (cause) {
                        is UnresolvedAddressException ->
                            NetworkException(NetworkErrorType.NO_INTERNET)
                        is SerializationException ->
                            NetworkException(NetworkErrorType.SERIALIZATION)
                        is ApiException.HttpError ->
                            cause
                        else ->
                            NetworkException(NetworkErrorType.UNKNOWN)
                    }
                }
            }
        }
    }
}