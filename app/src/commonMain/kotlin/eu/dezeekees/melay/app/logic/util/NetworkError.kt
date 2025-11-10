package eu.dezeekees.melay.app.logic.util

import kotlinx.serialization.Serializable

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

@Serializable
data class NetworkError(
    override val message: String = "",
    override val type: NetworkErrorType = NetworkErrorType.UNKNOWN,
): Error
