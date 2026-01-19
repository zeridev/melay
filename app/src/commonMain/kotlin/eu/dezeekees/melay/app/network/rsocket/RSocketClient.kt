package eu.dezeekees.melay.app.network.rsocket

import eu.dezeekees.melay.app.logic.`interface`.IRSocketClient
import eu.dezeekees.melay.app.network.HttpClientProvider
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.ktor.client.RSocketSupport
import io.rsocket.kotlin.ktor.client.rSocket

class RSocketClient(
    private val clientProvider: HttpClientProvider
): IRSocketClient {
    private var rSocket: RSocket? = null

    override val isConnected: Boolean
        get() = rSocket != null

    override suspend fun connect(url: String) {
        if (rSocket == null) {
            rSocket = clientProvider
                .get()
                .rSocket(url)
        }
    }

    override fun getRSocket(): RSocket {
        return rSocket ?: error("RSocket not connected")
    }
}
