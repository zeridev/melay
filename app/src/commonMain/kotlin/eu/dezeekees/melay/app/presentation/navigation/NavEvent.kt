package eu.dezeekees.melay.app.presentation.navigation

sealed interface NavEvent {
    data class ToRoute(val route: AppRoute) : NavEvent
    data class ToRouteClearingBackstack(val route: AppRoute) : NavEvent
    data object Back : NavEvent
}