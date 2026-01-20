package eu.dezeekees.melay.app.logic.service

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.`interface`.IRSocketClient
import eu.dezeekees.melay.app.logic.model.LocalUserData
import eu.dezeekees.melay.app.logic.model.message.CreateMessageRequest
import eu.dezeekees.melay.app.logic.model.message.MessageResponse
import eu.dezeekees.melay.app.logic.repository.MessageRepository
import eu.dezeekees.melay.app.logic.repository.UserDataStoreRepository
import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.common.rsocket.ConfiguredProtoBuf
import eu.dezeekees.melay.common.rsocket.Payload
import eu.dezeekees.melay.common.rsocket.decodeFromPayload
import io.rsocket.kotlin.payload.Payload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.io.readByteArray
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.UUID

class MessageService(
    private val messageRepository: MessageRepository,
    private val userDataStoreRepository: UserDataStoreRepository,
    private val rSocketClient: IRSocketClient
) {
    @OptIn(ExperimentalSerializationApi::class)
    private val proto = ConfiguredProtoBuf
    private val outgoing = MutableSharedFlow<Payload>()

    suspend fun getAllForChannel(channelId: UUID): Result<List<MessageResponse>, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return messageRepository.getAllForChannel(channelId, userData.remoteDomain)
    }

    suspend fun createMessage(request: CreateMessageRequest): Result<MessageResponse, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return messageRepository.createMessage(request, userData.remoteDomain)
    }

    suspend fun deleteMessage(messageId: UUID): Result<Unit, Error> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        return messageRepository.deleteMessage(messageId, userData.remoteDomain)
    }

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun stream(channelId: UUID): Flow<MessageResponse> {
        val userData = userDataStoreRepository.get() ?: LocalUserData()
        val websocketDomain = userData.remoteDomain.toWebSocketUrl()
        val websocketRoute = "${websocketDomain}${Routes.Socket.MelayClient.CONNECTION_ROUTE}"
        val stringId = channelId.toString()

        if (!rSocketClient.isConnected) rSocketClient.connect(websocketRoute)
        val rSocket = rSocketClient.getRSocket()

        return rSocket.requestChannel(
            Payload(route = "stream.{channel_id:${stringId}}.messages"),
            outgoing
        ).map { payload ->
            val message = proto.decodeFromPayload<MessageResponse>(payload)

            return@map message
        }
    }
}

fun String.toWebSocketUrl(): String {
    return when {
        startsWith("https://") -> replaceFirst("https://", "wss://")
        startsWith("http://") -> replaceFirst("http://", "ws://")
        else -> this // fallback if already ws/wss
    }
}
