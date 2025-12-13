package eu.dezeekees.melay.server.api.payload

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UuidRequest(
    @Contextual val uuid: UUID,
)
