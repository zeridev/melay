package eu.dezeekees.melay.server.api.config

import eu.dezeekees.melay.server.api.payload.ErrorResponse
import eu.dezeekees.melay.server.logic.exception.BadRequestException
import eu.dezeekees.melay.server.logic.exception.NotFoundException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configStatusPage() {
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message ?: "Unknown error occurred"))
        }

        exception<NotFoundException> { call, cause ->
            call.respond(HttpStatusCode.NotFound, ErrorResponse(cause.message ?: "Unknown error occurred"))
        }

        exception<Throwable> { call, cause ->
            cause.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse(cause.message ?: "Unknown error occurred"))
        }
    }
}