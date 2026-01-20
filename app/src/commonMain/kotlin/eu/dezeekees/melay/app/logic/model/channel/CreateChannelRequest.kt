package eu.dezeekees.melay.app.logic.model.channel

import eu.dezeekees.melay.common.payload.ChannelType
import eu.dezeekees.melay.common.payload.channel.CreateChannelCommand
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreateChannelRequest(
    override val name: String,
    override val position: Int,
    override val type: ChannelType,
    @Contextual override val communityId: UUID
): CreateChannelCommand
