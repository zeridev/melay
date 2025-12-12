package eu.dezeekees.melay.server.api.validator

import eu.dezeekees.melay.server.api.payload.community.UserCommunityMembershipRequest
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import java.util.UUID

fun RequestValidationConfig.userCommunityMembershipValidator() {
    validate<UserCommunityMembershipRequest> { request ->
        val reasons = mutableListOf<String>()

        val userId = runCatching { UUID.fromString(request.userId) }.getOrNull()
        if (userId == null) {
            reasons.add("user_id: Invalid UUID format")
        }

        val communityId = UUID.fromString(request.communityId)
        if(communityId == null) {
            reasons.add("community_id: Invalid UUID format")
        }

        if (reasons.isNotEmpty())
            ValidationResult.Invalid(reasons)

        ValidationResult.Valid
    }
}