package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.api.mapper.UserMapper
import eu.dezeekees.melay.server.api.payload.user.CreateUserRequest
import eu.dezeekees.melay.server.logic.service.UserService
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val userService by inject<UserService>()

    route(Routes.Api.User.NAME) {
        // Create a new user
        post {
            val request = call.receive<CreateUserRequest>()
            val user = userService.create(request.username, request.password)
            call.respond(UserMapper.toResponse(user))
        }

        authenticate("auth-jwt") {
            /**
             * @security bearer
             */
            get("/{id}") {
                val id = call.parameters["id"] ?: throw BadRequestException("Missing id")
                val user = userService.getById(id)
                call.respond(UserMapper.toResponse(user))
            }
        }
    }
}