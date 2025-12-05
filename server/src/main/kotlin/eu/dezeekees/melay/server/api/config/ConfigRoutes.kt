package eu.dezeekees.melay.server.api.config

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.api.routes.authRoutes
import eu.dezeekees.melay.server.api.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.*

fun Application.configRoutes() {
    routing {
        userRoutes()
        authRoutes()
    }
}