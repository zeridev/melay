package eu.dezeekees.melay.common.payload.message

import eu.dezeekees.melay.common.payload.user.UserResult
import kotlinx.datetime.Instant
import java.util.*

interface MessageResult {
    val id: UUID
    val channelId: UUID
    val author: UserResult
    val content: String
    val createdAt: Instant
    val editedAt: Instant?
}