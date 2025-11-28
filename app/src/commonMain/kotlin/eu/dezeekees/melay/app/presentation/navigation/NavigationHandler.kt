package eu.dezeekees.melay.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import org.koin.compose.koinInject

@Composable
fun NavigationHandler(
    navController: NavHostController,
    navigationManager: NavigationManager = koinInject()
) {
    LaunchedEffect(Unit) {
        navigationManager.events.collect { event ->
            when (event) {
                is NavEvent.ToRoute ->
                    navController.navigate(event.route)

                is NavEvent.ToRouteClearingBackstack -> {
                    navController.navigate(event.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }

                NavEvent.Back ->
                    navController.popBackStack()
            }
        }
    }
}