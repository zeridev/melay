package eu.dezeekees.melay.server.api.routes

import com.auth0.jwt.interfaces.Payload
import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.api.mapper.CommunityMapper
import eu.dezeekees.melay.server.api.mapper.UserMapper
import eu.dezeekees.melay.server.logic.exception.UnauthorizedException
import eu.dezeekees.melay.server.logic.service.UserCommunityMembershipService
import eu.dezeekees.melay.server.logic.service.UserService
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.UUID

fun Route.userRoutes() {
    val userService by inject<UserService>()
    val userCommunityMembershipService by inject<UserCommunityMembershipService>()

    authenticate("auth-jwt") {
        route(Routes.Api.User.NAME) {
            /** @security bearer */
            get("/{id}") {
                val id = call.parameters["id"] ?: throw BadRequestException("Missing id")
                val user = userService.getById(id)
                call.respond(UserMapper.toResponse(user))
            }
        }

        get(Routes.Api.User.ME) {
            val authPrincipal = call.principal<JWTPrincipal>()
                ?: throw UnauthorizedException("Missing user")
            val user = userService.getById(authPrincipal.payload.subject)
            call.respond(UserMapper.toResponse(user))
        }

        get(Routes.Api.User.COMMUNITIES) {
            val authPrincipal = call.principal<JWTPrincipal>()
                ?: throw UnauthorizedException("Not authenticated")
            val userId = runCatching { UUID.fromString(authPrincipal.payload.subject) }.getOrNull()
                ?: throw UnauthorizedException("Invalid UUID")

            val communities = userCommunityMembershipService.findCommunitiesForUser(userId)
            call.respond(communities.map(CommunityMapper::toResponse))
        }
    }
}