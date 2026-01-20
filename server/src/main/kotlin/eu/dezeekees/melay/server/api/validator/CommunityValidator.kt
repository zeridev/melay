package eu.dezeekees.melay.server.api.validator

import eu.dezeekees.melay.server.api.payload.community.CreateCommunityRequest
import eu.dezeekees.melay.server.api.payload.community.UpdateCommunityRequest
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.communityValidator() {
    validate<CreateCommunityRequest> { request ->
        val reasons = mutableListOf<String>()

        when {
            request.name.isBlank() ->
                reasons.add("name: Name must not be blank")
            request.name.length !in 3..100 ->
                reasons.add("name: Community name must be between 3 and 100 characters")
            !request.name.matches(Regex("^[a-z0-9_]+$")) ->
                reasons.add("name: Community name must be alphanumeric or use an underscore")
        }

        if(reasons.isNotEmpty())
            return@validate ValidationResult.Invalid(reasons)

        return@validate ValidationResult.Valid
    }

    validate<UpdateCommunityRequest> { request ->
        val reasons = mutableListOf<String>()

        if(request.name.isNotBlank()) {
            when {
                request.name.length !in 3..100 ->
                    reasons.add("name: Community name must be between 3 and 100 characters")
                !request.name.matches(Regex("^[a-z0-9_]+$")) ->
                    reasons.add("name: Community name must be alphanumeric or use an underscore")
            }
        }

        if(reasons.isNotEmpty())
            return@validate ValidationResult.Invalid(reasons)

        return@validate ValidationResult.Valid
    }
}