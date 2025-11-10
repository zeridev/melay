package eu.dezeekees.melay.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.dezeekees.melay.app.logic.AuthService
import eu.dezeekees.melay.app.logic.util.onError
import eu.dezeekees.melay.app.logic.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService,
): ViewModel() {
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

    fun login() {
        viewModelScope.launch {
            _uiState.update { state -> state.copy(isLoading = true) }
            authService.login(
                username = _uiState.value.username,
                password = _uiState.value.password,
                domain = _uiState.value.domain
            ).onError { error ->
                _uiState.update { state -> state.copy(isLoading = false) }
            }
            .onSuccess { response ->
                _uiState.update { state -> state.copy(isLoading = false) }
            }
        }
    }
}