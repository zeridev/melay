package eu.dezeekees.melay.server.federation.model

data class FederationMessage(
    val type: String,
    val communityId: String,
    val sender: String,
    val content: String
)

