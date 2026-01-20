package eu.dezeekees.melay.app.logic.model.auth

sealed class AuthState {
    object Unauthenticated : AuthState()
    data class Authenticated(val token: Token) : AuthState()
}