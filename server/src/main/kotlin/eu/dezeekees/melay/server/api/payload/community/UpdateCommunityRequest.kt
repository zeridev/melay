package eu.dezeekees.melay.server.api.payload.community

import eu.dezeekees.melay.common.payload.community.UpdateCommunityCommand
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UpdateCommunityRequest(
    @Contextual override val id: UUID,
    override val  name: String = "",
    override val description: String = "",
    override val iconUrl: String = "",
    override val bannerUrl: String = "",
): UpdateCommunityCommand
