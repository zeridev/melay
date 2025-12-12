package eu.dezeekees.melay.server.logic.model

import kotlinx.datetime.Instant
import java.util.UUID

data class Community(
    val id: UUID? = null,
    val name: String,
    var description: String = "",
    var iconUrl: String = "",
    var bannerUrl: String = "",
    val createdAt: Instant? = null,
    val channels: List<Channel> = emptyList()
)