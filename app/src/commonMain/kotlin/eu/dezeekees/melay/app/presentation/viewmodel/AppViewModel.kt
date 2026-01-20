package eu.dezeekees.melay.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import eu.dezeekees.melay.app.logic.`interface`.IAuthStateProvider
import eu.dezeekees.melay.app.logic.model.auth.AuthState
import kotlinx.coroutines.flow.StateFlow

class AppViewModel(
    authStateProvider: IAuthStateProvider,
): ViewModel() {
    val authState: StateFlow<AuthState> =
        authStateProvider.authState
}