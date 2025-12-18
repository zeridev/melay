package eu.dezeekees.melay.app.logic.error

import eu.dezeekees.melay.common.payload.ErrorResult

interface Error : ErrorResult {
    val type: NetworkErrorType
}