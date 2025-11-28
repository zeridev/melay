package eu.dezeekees.melay.app.logic.error

import eu.dezeekees.melay.common.result.ErrorResult

interface Error : ErrorResult {
    val type: NetworkErrorType
}