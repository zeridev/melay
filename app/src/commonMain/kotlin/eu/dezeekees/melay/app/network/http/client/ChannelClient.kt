package eu.dezeekees.melay.app.network.http.client

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.error.safeCall
import eu.dezeekees.melay.app.logic.model.channel.ChannelResponse
import eu.dezeekees.melay.app.logic.model.channel.CreateChannelRequest
import eu.dezeekees.melay.app.logic.repository.ChannelRepository
import eu.dezeekees.melay.app.network.HttpClientProvider
import eu.dezeekees.melay.common.Routes
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.util.UUID

class ChannelClient(
    private val clientProvider: HttpClientProvider,
): ChannelRepository {
    override suspend fun createChannel(request: CreateChannelRequest, domain: String): Result<ChannelResponse, Error> = safeCall {
        clientProvider
            .get()
            .post("${domain}${Routes.Api.Channel.NAME}") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
    }

    override suspend fun deleteChannel(id: UUID, domain: String): Result<Unit, Error> = safeCall {
        clientProvider
            .get()
            .delete("${domain}${Routes.Api.Channel.NAME}/${id}")
    }
}