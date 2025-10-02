package eu.dezeekees.melay.federation.model

data class FederationMessage(
    val type: String,
    val communityId: String,
    val sender: String,
    val content: String
)

