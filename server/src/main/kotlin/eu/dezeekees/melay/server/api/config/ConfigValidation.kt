package eu.dezeekees.melay.server.api.config

import eu.dezeekees.melay.server.api.validator.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configValidation() {
    install(RequestValidation) {
        userValidator()
        authValidator()
        communityValidator()
        channelValidator()
    }
}