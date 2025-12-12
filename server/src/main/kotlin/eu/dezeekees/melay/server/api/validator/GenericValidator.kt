package eu.dezeekees.melay.server.api.validator

import eu.dezeekees.melay.server.api.payload.UuidRequest
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import java.util.UUID

fun RequestValidationConfig.genericValidator() {

    validate<UuidRequest> { request ->
        val errors = mutableListOf<String>()

        val parsed = runCatching { UUID.fromString(request.uuid) }.getOrNull()
        if (parsed == null) {
            errors.add("uuid: Invalid UUID format")
        }

        if (errors.isNotEmpty())
            ValidationResult.Invalid(errors)

        ValidationResult.Valid
    }

}