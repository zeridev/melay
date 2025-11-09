package eu.dezeekees.melay.app.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    data class UiState(
        val domain: String = "",
        val username: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun onDomainChanged(newDomain: String) =
        _uiState.update { state -> state.copy(domain = newDomain) }

    fun onUsernameChanged(newUsername: String) =
        _uiState.update { state -> state.copy(username = newUsername) }

    fun onPasswordChanged(newPassword: String) =
        _uiState.update { state -> state.copy(password = newPassword) }
}