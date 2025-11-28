package eu.dezeekees.melay.app.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute

object AppRoutes {
    @Serializable object Main : AppRoute
    object Auth {
        @Serializable object Login : AppRoute
    }
}