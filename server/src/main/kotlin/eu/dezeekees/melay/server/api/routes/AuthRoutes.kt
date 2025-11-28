package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.api.mapper.AuthMapper
import eu.dezeekees.melay.server.api.mapper.UserMapper
import eu.dezeekees.melay.server.api.payload.auth.LoginRequest
import eu.dezeekees.melay.server.api.payload.auth.RegisterUserRequest
import eu.dezeekees.melay.server.logic.service.AuthService
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authRoutes() {
    val authService by inject<AuthService>()

    // Login Route
    post(Routes.Api.Auth.LOGIN) {
        val request = call.receive<LoginRequest>()
        val secret = environment.config.property("jwt.secret").getString()
        val token = authService.login(request.username, request.password, secret)
        call.respond(AuthMapper.toTokenResponse(token))
    }

    //Register Route
    post(Routes.Api.Auth.REGISTER) {
        val request = call.receive<RegisterUserRequest>()
        val user = authService.register(request.username, request.password)
        call.respond(UserMapper.toResponse(user))
    }
}