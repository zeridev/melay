package eu.dezeekees.melay.server.api.validator

import eu.dezeekees.melay.server.api.payload.channel.CreateChannelRequest
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.channelValidator() {
    validate<CreateChannelRequest> { request ->
        val reasons = mutableListOf<String>()

        when {
            request.name.isBlank() ->
                reasons.add("name: Channel name must not be blank")
            request.name.length !in 3..100 ->
                reasons.add("name: Channel name must be between 3 and 100 characters")
            !request.name.matches(Regex("^[a-z0-9_]+$")) ->
                reasons.add("name: Channel name must be alphanumeric or use an underscore")
        }

        if(reasons.isNotEmpty())
            return@validate ValidationResult.Invalid(reasons)

        return@validate ValidationResult.Valid
    }
}