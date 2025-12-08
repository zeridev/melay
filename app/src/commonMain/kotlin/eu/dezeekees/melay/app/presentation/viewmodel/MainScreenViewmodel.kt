package eu.dezeekees.melay.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.dezeekees.melay.app.logic.`interface`.IRSocketClient
import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.common.rsocket.ConfiguredProtoBuf
import eu.dezeekees.melay.common.rsocket.Payload
import eu.dezeekees.melay.common.rsocket.encodeToPayload
import io.ktor.utils.io.readText
import io.rsocket.kotlin.payload.Payload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.io.Buffer
import kotlinx.io.writeString
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
class MainScreenViewmodel(
    private val rSocketClient: IRSocketClient
): ViewModel() {
    @OptIn(ExperimentalSerializationApi::class)
    private val proto = ConfiguredProtoBuf
    private val outgoing = MutableSharedFlow<Payload>()
    lateinit var stream: Flow<Payload>

    init {
        viewModelScope.launch {
            if (!rSocketClient.isConnected) {
                rSocketClient.connect("ws://127.0.0.1:8080${Routes.Socket.MelayClient.CONNECTION_ROUTE}")
            }

            val rSocket = rSocketClient.getRSocket()

            stream = rSocket.requestChannel(
                Payload(route = "stream.{community_id:1}.{channel_id:1}.messages"),
                outgoing
            )

            stream.collect { payload ->
                println("Received payload: ${payload.data.readText()}")
            }
        }
    }

}