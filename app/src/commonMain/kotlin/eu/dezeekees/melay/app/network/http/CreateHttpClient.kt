package eu.dezeekees.melay.app.network.http

import eu.dezeekees.melay.app.logic.error.NetworkError
import eu.dezeekees.melay.app.logic.serializer.NetworkErrorSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

expect fun createHttpClientEngine(): HttpClientEngine

fun createHttpClient(): HttpClient {
    return HttpClient(createHttpClientEngine()) {
        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.SIMPLE
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    serializersModule = SerializersModule {
                        contextual(NetworkError::class, NetworkErrorSerializer)
                    }
                }
            )
        }
    }
}