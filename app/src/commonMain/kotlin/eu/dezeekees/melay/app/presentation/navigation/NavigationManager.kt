package eu.dezeekees.melay.app.presentation.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigationManager {
    private val _events = MutableSharedFlow<NavEvent>()
    val events = _events.asSharedFlow()

    suspend fun navigate(event: NavEvent) {
        _events.emit(event)
    }
}