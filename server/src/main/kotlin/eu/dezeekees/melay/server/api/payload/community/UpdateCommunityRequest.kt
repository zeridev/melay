package eu.dezeekees.melay.server.api.payload.community

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UpdateCommunityRequest(
    @Contextual val id: UUID,
    val name: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val bannerUrl: String = "",
)
