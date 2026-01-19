package eu.dezeekees.melay.common.rsocket

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.common.kotlinx.UUIDSerializer
import io.rsocket.kotlin.ExperimentalMetadataApi
import io.rsocket.kotlin.metadata.RoutingMetadata
import io.rsocket.kotlin.metadata.metadata
import io.rsocket.kotlin.payload.Payload
import io.rsocket.kotlin.payload.buildPayload
import io.rsocket.kotlin.payload.data
import kotlinx.io.Buffer
import kotlinx.io.readByteArray
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.protobuf.ProtoBuf
import java.util.UUID

@ExperimentalSerializationApi
val ConfiguredProtoBuf = ProtoBuf {
    serializersModule = SerializersModule {
        contextual(UUID::class, UUIDSerializer)
    }
}

@ExperimentalSerializationApi
inline fun <reified T> ProtoBuf.decodeFromPayload(payload: Payload): T = decodeFromByteArray(payload.data.readByteArray())

@ExperimentalSerializationApi
@OptIn(ExperimentalMetadataApi::class)
inline fun <reified T> ProtoBuf.encodeToPayload(route: String, value: T): Payload = buildPayload {
    data(encodeToByteArray(value))
    metadata(RoutingMetadata(route))
}

@ExperimentalSerializationApi
inline fun <reified T> ProtoBuf.encodeToPayload(value: T): Payload = buildPayload {
    data(encodeToByteArray(value))
}

// receives a values and returns a value
// for requests that do return a response, for example sending a message
@ExperimentalSerializationApi
@JvmName("decoding2")
inline fun <reified I, reified O> ProtoBuf.decoding(
    payload: Payload,
    block: (I) -> O
): Payload {
    return encodeToPayload(decodeFromPayload<I>(payload).let(block))
}

// receives a values returns empty
// for requests without a response, for example a delete request
@ExperimentalSerializationApi
inline fun <reified I> ProtoBuf.decoding(
    payload: Payload,
    block: (I) -> Unit
): Payload {
    decodeFromPayload<I>(payload).let(block)
    return Payload.Empty
}

@OptIn(ExperimentalMetadataApi::class)
fun Payload(route: String, data: Buffer = Buffer()): Payload = buildPayload {
    data(data)
    metadata(RoutingMetadata(route))
}

