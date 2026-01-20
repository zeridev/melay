package eu.dezeekees.melay.server.api.util

import eu.dezeekees.melay.server.logic.exception.BadRequestException
import io.ktor.http.Parameters
import java.util.UUID

fun Parameters.getUUIDFromParam(param: String): UUID {
    val communityIdString = this[param]
        ?: throw BadRequestException("parameter with name $param missing")
    return runCatching { UUID.fromString(communityIdString) }.getOrNull()
        ?: throw BadRequestException("parameter with name $param missing or invalid")
}