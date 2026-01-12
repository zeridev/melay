package eu.dezeekees.melay.app.logic.error

import io.ktor.http.HttpStatusCode

enum class NetworkErrorType {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    BAD_REQUEST,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN;
}

fun HttpStatusCode.toNetworkErrorType(): NetworkErrorType =
    when (value) {
        400 -> NetworkErrorType.BAD_REQUEST
        401 -> NetworkErrorType.UNAUTHORIZED
        408 -> NetworkErrorType.REQUEST_TIMEOUT
        409 -> NetworkErrorType.CONFLICT
        413 -> NetworkErrorType.PAYLOAD_TOO_LARGE
        429 -> NetworkErrorType.TOO_MANY_REQUESTS
        in 500..599 -> NetworkErrorType.SERVER_ERROR
        else -> NetworkErrorType.UNKNOWN
    }

class NetworkError(
    override val type: NetworkErrorType = NetworkErrorType.UNKNOWN,
    private val _reasons: MutableList<String> = mutableListOf()
): Error {

    override val reasons: List<String>
        get() {
            return _reasons.map { reason ->
                reason.split(":")[1]
            }
        }

    fun getReasonForField(fieldMatch: String): String {
        val reason = _reasons.firstOrNull { reason ->
            reason.substringBefore(":").trim() == fieldMatch
        }?.substringAfter(":")?.trim()

        return reason ?: ""
    }

    fun copy(
        type: NetworkErrorType = this.type,
        reasons: List<String> = this._reasons.toList()
    ): NetworkError {
        // Make a new mutable list for the copy
        return NetworkError(type, reasons.toMutableList())
    }
}