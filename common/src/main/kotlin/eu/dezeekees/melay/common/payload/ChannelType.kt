package eu.dezeekees.melay.common.payload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ChannelType {
    @SerialName("TEXT") TEXT,
    @SerialName("VOICE") VOICE
}