package eu.dezeekees.melay.server.api.payload.community

import eu.dezeekees.melay.common.payload.community.CreateCommunityCommand
import kotlinx.serialization.Serializable

@Serializable
data class CreateCommunityRequest(
    override val name: String,
    override val description: String,
): CreateCommunityCommand
