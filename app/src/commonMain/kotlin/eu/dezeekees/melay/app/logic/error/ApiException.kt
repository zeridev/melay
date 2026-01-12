package eu.dezeekees.melay.app.logic.error

import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

sealed class ApiException : Exception() {
    data class HttpError(
        val status: HttpStatusCode,
        val response: HttpResponse? = null
    ) : ApiException()
}