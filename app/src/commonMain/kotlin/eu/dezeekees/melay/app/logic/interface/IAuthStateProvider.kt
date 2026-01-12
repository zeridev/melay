package eu.dezeekees.melay.app.logic.`interface`

import eu.dezeekees.melay.app.logic.model.auth.AuthState
import kotlinx.coroutines.flow.StateFlow

interface IAuthStateProvider {
    val authState: StateFlow<AuthState>
}