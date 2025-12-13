package eu.dezeekees.melay.server.logic.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ChannelType {
    @SerialName("TEXT") TEXT,
    @SerialName("VOICE") VOICE
}