package eu.dezeekees.melay.app.logic.repository

import eu.dezeekees.melay.app.logic.model.auth.LoginRequest
import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.app.logic.util.Error
import eu.dezeekees.melay.app.logic.util.Result

interface AuthRepository {
    suspend fun register()
    suspend fun login(request: LoginRequest, domain: String): Result<Token, Error>
}