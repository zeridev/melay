package eu.dezeekees.melay.server.api.payload

import eu.dezeekees.melay.common.result.ErrorResult
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    override val reasons: List<String>,
): ErrorResult
