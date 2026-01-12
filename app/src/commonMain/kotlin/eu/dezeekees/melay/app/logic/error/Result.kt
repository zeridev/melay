package eu.dezeekees.melay.app.logic.error

import io.ktor.client.call.body

sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: eu.dezeekees.melay.app.logic.error.Error>(val error: E): Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

suspend inline fun <reified T> safeCall(
    block: suspend () -> T
): Result<T, Error> =
    try {
        Result.Success(block())
    } catch (e: ApiException.HttpError) {

        val errorType = e.status.toNetworkErrorType()

        val error = if (errorType == NetworkErrorType.BAD_REQUEST && e.response != null) {
            runCatching {
                e.response.body<NetworkError>()
                    .copy(type = NetworkErrorType.BAD_REQUEST)
            }.getOrElse {
                NetworkError(type = NetworkErrorType.BAD_REQUEST)
            }
        } else {
            NetworkError(type = errorType)
        }

        Result.Error(error)

    } catch (e: NetworkException) {
        Result.Error(NetworkError(type = e.type))
    } catch (e: Exception) {
        Result.Error(NetworkError())
    }

typealias EmptyResult<E> = Result<Unit, E>

