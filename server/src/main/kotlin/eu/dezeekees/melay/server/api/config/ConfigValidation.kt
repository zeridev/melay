package eu.dezeekees.melay.server.api.config

import eu.dezeekees.melay.server.api.validator.authValidator
import eu.dezeekees.melay.server.api.validator.userValidator
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configValidation() {
    install(RequestValidation) {
        userValidator()
        authValidator()
    }
}