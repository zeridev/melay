package eu.dezeekees.melay.server.logic.broadcast

import eu.dezeekees.melay.server.logic.model.Message
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.get

class MessageBroadcaster {
    private val streams = ConcurrentHashMap<UUID, MutableSharedFlow<Message>>()

    fun stream(channelId: UUID): SharedFlow<Message> =
        streams.getOrPut(channelId) { MutableSharedFlow(replay=0, extraBufferCapacity=64) }
            .asSharedFlow()

    suspend fun broadcast(message: Message) {
        streams[message.channelId]?.emit(message)
    }
}
