package eu.dezeekees.melay.server.api.validator

import eu.dezeekees.melay.server.api.payload.auth.RegisterUserRequest
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.userValidator() {
    validate<RegisterUserRequest> { request ->
        when {
            request.username.isBlank() -> return@validate ValidationResult.Invalid("User name must not be blank")
            request.username.length !in 3..32 -> return@validate ValidationResult.Invalid("User name must be between 3 and 32 characters")
            !request.username.matches(Regex("^[a-z0-9_]+$")) -> return@validate ValidationResult.Invalid("User name must be alphanumeric or use an underscore")
            request.password.isBlank() -> return@validate ValidationResult.Invalid("Password must not be blank")
            request.password.length < 6 -> return@validate ValidationResult.Invalid("Password must be at least 6 characters")
        }

        ValidationResult.Valid
    }
}