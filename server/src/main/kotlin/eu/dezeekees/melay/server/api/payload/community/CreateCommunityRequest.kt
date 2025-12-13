package eu.dezeekees.melay.server.api.payload.community

import kotlinx.serialization.Serializable

@Serializable
data class CreateCommunityRequest(
    val name: String,
    val description: String,
)
