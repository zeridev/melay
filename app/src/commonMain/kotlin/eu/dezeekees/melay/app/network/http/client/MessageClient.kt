package eu.dezeekees.melay.app.network.http.client

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.error.safeCall
import eu.dezeekees.melay.app.logic.model.message.CreateMessageRequest
import eu.dezeekees.melay.app.logic.model.message.MessageResponse
import eu.dezeekees.melay.app.logic.repository.MessageRepository
import eu.dezeekees.melay.app.network.HttpClientProvider
import eu.dezeekees.melay.common.Routes
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.util.UUID

class MessageClient(
    private val httpClientProvider: HttpClientProvider
): MessageRepository {
    override suspend fun getAllForChannel(channelId: UUID, domain: String): Result<List<MessageResponse>, Error> = safeCall {
        httpClientProvider
            .get()
            .get("${domain}${Routes.Api.Message.CHANNEL}/$channelId")
            .body()
    }

    override suspend fun createMessage(request: CreateMessageRequest, domain: String): Result<MessageResponse, Error> = safeCall {
        httpClientProvider
            .get()
            .post("${domain}${Routes.Api.Message.NAME}") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body()
    }

    override suspend fun deleteMessage(messageId: UUID, domain: String): Result<Unit, Error> = safeCall {
        httpClientProvider
            .get()
            .delete("${domain}${Routes.Api.Message.CHANNEL}/$messageId")
    }
}