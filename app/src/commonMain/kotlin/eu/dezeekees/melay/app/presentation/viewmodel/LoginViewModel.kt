package eu.dezeekees.melay.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.dezeekees.melay.app.logic.AuthService
import eu.dezeekees.melay.app.logic.error.NetworkError
import eu.dezeekees.melay.app.logic.error.onError
import eu.dezeekees.melay.app.logic.error.onSuccess
import eu.dezeekees.melay.app.presentation.navigation.AppRoutes
import eu.dezeekees.melay.app.presentation.navigation.NavEvent
import eu.dezeekees.melay.app.presentation.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService,
    private val navigationManager: NavigationManager
): ViewModel() {
    data class UiState(
        val domain: String = "",
        val username: String = "",
        val password: String = "",
        val usernameError: String = "",
        val passwordError: String = "",
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
            ).onError {
                val error = it as NetworkError
                _uiState.update { state -> state.copy(
                    isLoading = false,
                    usernameError = error.getReasonForField("username"),
                    passwordError = error.getReasonForField("password")
                ) }
            }
            .onSuccess {
                _uiState.update { state -> state.copy(isLoading = false) }
                navigationManager.navigate(NavEvent.ToRouteClearingBackstack(AppRoutes.Main))
            }
        }
    }
}