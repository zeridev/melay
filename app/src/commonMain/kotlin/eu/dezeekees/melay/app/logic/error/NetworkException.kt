package eu.dezeekees.melay.app.logic.error

class NetworkException(
    val type: NetworkErrorType
) : Exception()