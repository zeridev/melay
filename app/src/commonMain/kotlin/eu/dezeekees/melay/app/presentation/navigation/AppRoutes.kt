package eu.dezeekees.melay.app.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute

object AppRoutes {
    object Auth {
        @Serializable object Login : AppRoute
    }
}