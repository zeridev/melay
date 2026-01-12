package eu.dezeekees.melay.app.logic.model.channel

import eu.dezeekees.melay.common.payload.ChannelType
import eu.dezeekees.melay.common.payload.channel.ChannelResult
import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ChannelResponse(
    @Contextual override val id: UUID,
    override val name: String,
    override val type: ChannelType,
    override val position: Int,
    override val createdAt: Instant
): ChannelResult
