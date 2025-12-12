package eu.dezeekees.melay.server.api.payload.community

data class CreateCommunityRequest(
    val name: String,
    val description: String,
)
