package eu.dezeekees.melay.app.logic.`interface`

import io.rsocket.kotlin.RSocket
import kotlinx.coroutines.flow.Flow

interface IRSocketClient {

    /** Whether the client is currently connected */
    val isConnected: Boolean

    /** Connect to the server; suspend because establishing a connection is async */
    suspend fun connect(url: String)

    /** Access the underlying RSocket instance (optional) */
    fun getRSocket(): RSocket
}
