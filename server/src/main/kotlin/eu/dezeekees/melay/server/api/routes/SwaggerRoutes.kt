package eu.dezeekees.melay.server.api.routes

import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.Route

fun Route.swaggerRoutes() {
    val enableSwagger = environment.config.property("ktor.swagger.enable").getString().toBoolean()

    if(enableSwagger) {
        swaggerUI(
            path = "/swagger-ui",
            swaggerFile = "openapi/generated.json"
        )
    }
}
