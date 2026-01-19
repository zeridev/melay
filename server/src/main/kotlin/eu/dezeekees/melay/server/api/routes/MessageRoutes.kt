package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.api.mapper.MessageMapper
import eu.dezeekees.melay.server.api.payload.message.CreateMessageRequest
import eu.dezeekees.melay.server.api.payload.message.UpdateMessageRequest
import eu.dezeekees.melay.server.api.util.getUUIDFromParam
import eu.dezeekees.melay.server.logic.exception.UnauthorizedException
import eu.dezeekees.melay.server.logic.service.MessageService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.UUID

fun Route.messageRoutes() {
    val messageService by inject<MessageService>()

    authenticate("auth-jwt") {
        route(Routes.Api.Message.NAME) {
            post {
                val authPrincipal = call.principal<JWTPrincipal>()
                    ?: throw UnauthorizedException("Not authenticated")
                val userId = runCatching { UUID.fromString(authPrincipal.payload.subject) }.getOrNull()
                    ?: throw UnauthorizedException("Invalid UUID")

                val request = call.receive<CreateMessageRequest>()
                val message = messageService.create(MessageMapper.toModel(request), userId)
                call.respond(MessageMapper.toResponse(message))
            }

            patch {
                val request = call.receive<UpdateMessageRequest>()
                val message =
                    messageService.update(MessageMapper.toModel(request))
                call.respond(MessageMapper.toResponse(message))
            }

            delete("/{messageId}") {
                val messageId =
                    call.parameters.getUUIDFromParam("messageId")
                messageService.delete(messageId)
                call.respond(HttpStatusCode.OK)
            }
        }

        get("${Routes.Api.Message.CHANNEL}/{channelId}") {
            val channelId = call.parameters.getUUIDFromParam("channelId")
            val messages = messageService.getAllForChannel(channelId)
            call.respond(messages.map { MessageMapper.toResponse(it) })
        }
    }
}
