package eu.dezeekees.melay.common.payload.community

import java.util.UUID

interface UpdateCommunityCommand {
    val id: UUID
    val name: String
    val description: String
    val iconUrl: String
    val bannerUrl: String
}