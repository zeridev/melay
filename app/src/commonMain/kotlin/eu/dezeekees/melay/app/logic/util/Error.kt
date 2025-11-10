package eu.dezeekees.melay.app.logic.util

import eu.dezeekees.melay.common.result.ErrorResult

interface Error : ErrorResult {
    val type: NetworkErrorType
    override val message: String
}