package eu.dezeekees.melay.app.logic.serializer

import eu.dezeekees.melay.app.logic.error.NetworkError
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

object NetworkErrorSerializer: KSerializer<NetworkError> {
    override val descriptor = buildClassSerialDescriptor("NetworkError") {
        element<List<String>>("reasons")
    }

    override fun serialize(
        encoder: Encoder,
        value: NetworkError
    ) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(
                descriptor,
                0,
                ListSerializer(String.serializer()),
                value.reasons
            )
        }
    }

    override fun deserialize(decoder: Decoder): NetworkError {
        var reasons: List<String> = emptyList()

        decoder.decodeStructure(descriptor) {
            loop@ while (true) {
                when (val i = decodeElementIndex(descriptor)) {
                    0 -> {
                        reasons = decodeSerializableElement(
                            descriptor,
                            0,
                            ListSerializer(String.serializer()),
                        )
                    }
                    CompositeDecoder.DECODE_DONE -> break@loop
                    else -> error("Unexpected index $i")
                }
            }
        }

        return NetworkError(
            _reasons = reasons.toMutableList()
        )
    }
}