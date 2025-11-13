package eu.dezeekees.melay.app.logic

import eu.dezeekees.melay.app.logic.model.auth.LoginRequest
import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.app.logic.repository.AuthRepository
import eu.dezeekees.melay.app.logic.repository.TokenStoreRepository
import eu.dezeekees.melay.app.logic.util.Error
import eu.dezeekees.melay.app.logic.util.Result
import eu.dezeekees.melay.app.logic.util.onSuccess

class AuthService(
    private val authRepository: AuthRepository,
    private val tokenStoreRepository: TokenStoreRepository
) {
    suspend fun login(
        domain: String,
        username: String,
        password: String
    ): Result<Token, Error> =
        authRepository.login(
            LoginRequest(
                username = username,
                password = password
            ),
            domain
        ).onSuccess {
            tokenStoreRepository.set(it)
        }
}