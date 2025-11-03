package eu.dezeekees.melay.common.result

import java.time.LocalDateTime
import java.util.UUID

interface UserResult {
    val id: UUID?
    val username: String
    val displayName: String
    var profilePicture: String?
    var profileDescription: String?
    val createdAt: LocalDateTime?
}