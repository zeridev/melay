package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.api.mapper.UserMapper
import eu.dezeekees.melay.server.logic.service.UserService
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val userService by inject<UserService>()

    route(Routes.Api.User.NAME) {
        authenticate("auth-jwt") {

            /** @security bearer */
            get("/{id}") {
                val id = call.parameters["id"] ?: throw BadRequestException("Missing id")
                val user = userService.getById(id)
                call.respond(UserMapper.toResponse(user))
            }
        }
    }
}