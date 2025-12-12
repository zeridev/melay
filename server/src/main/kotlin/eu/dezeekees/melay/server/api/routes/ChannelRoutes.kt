package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.api.mapper.ChannelMapper
import eu.dezeekees.melay.server.api.payload.UuidRequest
import eu.dezeekees.melay.server.api.payload.channel.ChannelResponse
import eu.dezeekees.melay.server.api.payload.channel.CreateChannelRequest
import eu.dezeekees.melay.server.api.payload.channel.UpdateChannelRequest
import eu.dezeekees.melay.server.logic.service.ChannelService
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import java.util.UUID

fun Route.channelRoutes() {
    val channelService by inject<ChannelService>()

    route(Routes.Api.Channel.NAME) {
        post<ChannelResponse> {
            val request = call.receive<CreateChannelRequest>()
            val channel = channelService.create(ChannelMapper.toModel(request))
            call.respond(ChannelMapper.toResponse(channel))
        }

        patch<ChannelResponse> {
            val request = call.receive<UpdateChannelRequest>()
            val channel = channelService.update(ChannelMapper.toModel(request))
            call.respond(ChannelMapper.toResponse(channel))
        }

        delete {
            val request = call.receive<UuidRequest>()
            val uuid = UUID.fromString(request.uuid)
            channelService.delete(uuid)
        }
    }


}