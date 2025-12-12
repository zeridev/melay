package eu.dezeekees.melay.server.api.payload.community

data class UserCommunityMembershipRequest(
    val userId: String,
    val communityId: String,
)