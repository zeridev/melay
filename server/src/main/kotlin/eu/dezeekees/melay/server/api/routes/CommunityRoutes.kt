package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.api.mapper.CommunityMapper
import eu.dezeekees.melay.server.api.mapper.UserMapper
import eu.dezeekees.melay.server.api.payload.UuidRequest
import eu.dezeekees.melay.server.api.payload.community.CommunityResponse
import eu.dezeekees.melay.server.api.payload.community.CreateCommunityRequest
import eu.dezeekees.melay.server.api.payload.community.UpdateCommunityRequest
import eu.dezeekees.melay.server.api.payload.community.UserCommunityMembershipRequest
import eu.dezeekees.melay.server.logic.exception.BadRequestException
import eu.dezeekees.melay.server.logic.service.CommunityService
import eu.dezeekees.melay.server.logic.service.UserCommunityMembershipService
import io.ktor.server.auth.authenticate
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.communityRoutes() {
    val communityService by inject<CommunityService>()
    val userCommunityMembershipService by inject<UserCommunityMembershipService>()

    authenticate("auth-jwt") {
        route(Routes.Api.Community.NAME) {

            get {
                val communities = communityService.findAll()
                call.respond(CommunityMapper.toResponse(communities))
            }

            post {
                val request = call.receive<CreateCommunityRequest>()
                val community = communityService.create(CommunityMapper.toModel(request))
                call.respond(CommunityMapper.toResponse(community))
            }

            patch {
                val request = call.receive<UpdateCommunityRequest>()
                val community = communityService.update(CommunityMapper.toModel(request))
                call.respond(CommunityMapper.toResponse(community))
            }

            delete {
                val request = call.receive<UuidRequest>()
                val uuid = request.uuid
                communityService.delete(uuid)
            }
        }

        route(Routes.Api.Community.MEMBERS) {
            get("/{communityId}") {
                val communityIdString = call.parameters["communityId"]
                    ?: throw BadRequestException("CommunityId parameter missing")
                val communityId = runCatching { UUID.fromString(communityIdString) }.getOrNull()
                    ?: throw BadRequestException("CommunityId parameter missing or invalid")

                val members = userCommunityMembershipService.findUsersForCommunity(communityId)
                members.map(UserMapper::toResponse)
            }

            post {
                val request = call.receive<UserCommunityMembershipRequest>()
                val communityId = request.communityId
                userCommunityMembershipService.addMembership(request.userId, communityId)

                val community = communityService.findById(communityId)
                call.respond(CommunityMapper.toResponse(community))
            }

            delete {
                val request = call.receive<UserCommunityMembershipRequest>()
                userCommunityMembershipService.removeMembership(request.userId, request.communityId)
            }
        }
    }

}