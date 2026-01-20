package eu.dezeekees.melay.app.logic.service

import eu.dezeekees.melay.app.logic.error.Error
import eu.dezeekees.melay.app.logic.error.Result
import eu.dezeekees.melay.app.logic.error.onSuccess
import eu.dezeekees.melay.app.logic.model.LocalUserData
import eu.dezeekees.melay.app.logic.model.auth.AuthState
import eu.dezeekees.melay.app.logic.model.auth.LoginRequest
import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.app.logic.repository.AuthRepository
import eu.dezeekees.melay.app.logic.repository.TokenStoreRepository
import eu.dezeekees.melay.app.logic.repository.UserDataStoreRepository
import eu.dezeekees.melay.app.network.HttpClientProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AuthService(
    private val authRepository: AuthRepository,
    private val tokenStoreRepository: TokenStoreRepository,
    private val userDataStoreRepository: UserDataStoreRepository,
    private val httpClientProvider: HttpClientProvider
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
            httpClientProvider.invalidate()
            val userData = userDataStoreRepository.get() ?: LocalUserData()
            val updatedLocalUserData = userData.copy(
                remoteDomain = domain
            )
            userDataStoreRepository.set(updatedLocalUserData)
        }
}