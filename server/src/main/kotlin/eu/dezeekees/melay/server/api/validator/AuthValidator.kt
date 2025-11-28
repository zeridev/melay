package eu.dezeekees.melay.server.api.validator

import eu.dezeekees.melay.server.api.payload.ErrorResponse
import eu.dezeekees.melay.server.api.payload.auth.LoginRequest
import eu.dezeekees.melay.server.api.payload.auth.RegisterUserRequest
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.authValidator() {
    validate<LoginRequest> { request ->
        val reasons = mutableListOf<String>()

        if(request.username.isBlank())
            reasons += "username: Username cannot be blank"

        if(request.password.isBlank())
            reasons += "password: Password cannot be blank"

        if (reasons.isNotEmpty())
            return@validate ValidationResult.Invalid(reasons)

        ValidationResult.Valid
    }

    validate<RegisterUserRequest> { request ->
        val reasons = mutableListOf<String>()

        when {
            request.username.isBlank() ->
                reasons.add("username: User name must not be blank")
            request.username.length !in 3..32 ->
                reasons.add("username: User name must be between 3 and 32 characters")
            !request.username.matches(Regex("^[a-z0-9_]+$")) ->
                reasons.add("username: User name must be alphanumeric or use an underscore")
        }

        when {
            request.password.isBlank() ->
                reasons.add("password: Password must not be blank")
            request.password.length < 6 ->
                reasons.add("password: Password must be at least 6 characters")
        }

        if(reasons.isNotEmpty())
            return@validate ValidationResult.Invalid(reasons)

        return@validate ValidationResult.Valid
    }
}