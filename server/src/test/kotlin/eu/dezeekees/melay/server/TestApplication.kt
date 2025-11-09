package eu.dezeekees.melay.server

import eu.dezeekees.melay.server.api.config.*
import io.ktor.server.application.Application

fun Application.module() {
    configContentNegotiation()
    configAuth()
    configStatusPage()
    configValidation()
    configRoutes()
}