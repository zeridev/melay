package eu.dezeekees.melay.app.logic.service

import eu.dezeekees.melay.app.logic.`interface`.IAuthStateProvider
import eu.dezeekees.melay.app.logic.model.auth.AuthState
import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.app.logic.repository.TokenStoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AuthStateProvider(
    tokenStoreRepository: TokenStoreRepository,
): IAuthStateProvider {
    override val authState: StateFlow<AuthState> =
        tokenStoreRepository.flow
            .map { token ->
                if (token?.token?.isNotBlank() ?: false)
                    AuthState.Authenticated(token)
                else
                    AuthState.Unauthenticated
            }
            .stateIn(
                CoroutineScope(SupervisorJob() + Dispatchers.IO),
                SharingStarted.Eagerly,
                AuthState.Unauthenticated
            )
}