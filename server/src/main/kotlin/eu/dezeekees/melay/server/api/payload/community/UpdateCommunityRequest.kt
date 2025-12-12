package eu.dezeekees.melay.server.api.payload.community

data class UpdateCommunityRequest(
    val name: String = "",
    val description: String = "",
    val iconUrl: String = "",
    val bannerUrl: String = "",
)
