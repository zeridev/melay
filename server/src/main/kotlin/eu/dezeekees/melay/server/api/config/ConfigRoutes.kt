package eu.dezeekees.melay.server.api.config

import eu.dezeekees.melay.server.api.routes.authRoutes
import eu.dezeekees.melay.server.api.routes.channelRoutes
import eu.dezeekees.melay.server.api.routes.communityRoutes
import eu.dezeekees.melay.server.api.routes.messageRoutes
import eu.dezeekees.melay.server.api.routes.userRoutes
import eu.dezeekees.melay.server.api.rsocket.routing.rsocketRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configRoutes() {
    routing {
        userRoutes()
        authRoutes()
        communityRoutes()
        channelRoutes()
        messageRoutes()
        rsocketRoutes()
//        swaggerRoutes()
    }
}