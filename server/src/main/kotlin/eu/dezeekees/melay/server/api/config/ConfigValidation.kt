package eu.dezeekees.melay.server.api.config

import eu.dezeekees.melay.server.api.validator.authValidator
import eu.dezeekees.melay.server.api.validator.channelValidator
import eu.dezeekees.melay.server.api.validator.communityValidator
import eu.dezeekees.melay.server.api.validator.genericValidator
import eu.dezeekees.melay.server.api.validator.userCommunityMembershipValidator
import eu.dezeekees.melay.server.api.validator.userValidator
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configValidation() {
    install(RequestValidation) {
        genericValidator()
        userValidator()
        authValidator()
        communityValidator()
        channelValidator()
        userCommunityMembershipValidator()
    }
}