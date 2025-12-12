package eu.dezeekees.melay.server.api.mapper

import eu.dezeekees.melay.server.api.payload.community.CommunityResponse
import eu.dezeekees.melay.server.api.payload.community.CreateCommunityRequest
import eu.dezeekees.melay.server.api.payload.community.UpdateCommunityRequest
import eu.dezeekees.melay.server.logic.exception.InternalServerErrorException
import eu.dezeekees.melay.server.logic.model.Community

object CommunityMapper {
    fun toModel(createCommunityRequest: CreateCommunityRequest) = Community(
        name = createCommunityRequest.name,
        description = createCommunityRequest.description,
    )

    fun toModel(updateCommunityRequest: UpdateCommunityRequest) = Community(
        name = updateCommunityRequest.name,
        description = updateCommunityRequest.description,
        iconUrl = updateCommunityRequest.iconUrl,
        bannerUrl = updateCommunityRequest.bannerUrl,
    )

    fun toResponse(community: Community): CommunityResponse = CommunityResponse(
        id = community.id ?: throw InternalServerErrorException("Community id is null"),
        name = community.name,
        description = community.description,
        iconUrl = community.iconUrl,
        bannerUrl = community.bannerUrl,
        createdAt = community.createdAt ?: throw InternalServerErrorException("Community createdAt is null"),
        channels = community.channels.map { ChannelMapper.toResponse(it) },
    )
}