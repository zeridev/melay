package eu.dezeekees.melay.app.logic.service

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.error.onSuccess
import eu.dezeekees.melay.app.logic.model.LocalUserData
import eu.dezeekees.melay.app.logic.model.auth.LoginRequest
import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.app.logic.repository.AuthRepository
import eu.dezeekees.melay.app.logic.repository.TokenStoreRepository
import eu.dezeekees.melay.app.logic.repository.UserDataStoreRepository

class AuthService(
    private val authRepository: AuthRepository,
    private val tokenStoreRepository: TokenStoreRepository,
    private val userDataStoreRepository: UserDataStoreRepository
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
            val userData = userDataStoreRepository.get() ?: LocalUserData()
            val updatedLocalUserData = userData.copy(
                remoteDomain = domain
            )
            userDataStoreRepository.set(updatedLocalUserData)
        }

    suspend fun tokenIsSet(): Boolean {
        val token = tokenStoreRepository.get() ?: return false
        return token.token.isNotBlank()
    }

    suspend fun getToken(): Token? {
        return tokenStoreRepository.get()
    }
}