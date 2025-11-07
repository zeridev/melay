package eu.dezeekees.melay.server.api.validator

import eu.dezeekees.melay.server.api.payload.auth.LoginRequest
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.authValidator() {
    validate<LoginRequest> { request ->
        when {
            request.username.isBlank() -> return@validate ValidationResult.Invalid("Username must not be blank")
            request.password.isBlank() -> return@validate ValidationResult.Invalid("Password must not be blank")
        }

        ValidationResult.Valid
    }
}