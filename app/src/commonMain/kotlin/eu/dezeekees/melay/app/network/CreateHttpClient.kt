package eu.dezeekees.melay.app.network

import eu.dezeekees.melay.app.logic.error.NetworkError
import eu.dezeekees.melay.app.logic.serializer.NetworkErrorSerializer
import eu.dezeekees.melay.common.kotlinx.UUIDSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import io.rsocket.kotlin.ktor.client.RSocketSupport
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.util.UUID

expect fun createHttpClientEngine(): HttpClientEngine

fun createHttpClient(): HttpClient {
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
    }
}