package eu.dezeekees.melay.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.dezeekees.melay.app.logic.model.auth.AuthState
import eu.dezeekees.melay.app.presentation.ui.screen.LoginScreen
import eu.dezeekees.melay.app.presentation.ui.screen.MainScreen
import eu.dezeekees.melay.app.presentation.viewmodel.AppViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: AppViewModel = koinViewModel(),
) {
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            AuthState.Unauthenticated -> {
                navController.navigate(AppRoutes.Auth.Login) {
                    popUpTo(0) { inclusive = true }
                }
            }
            is AuthState.Authenticated -> {
                navController.navigate(AppRoutes.Main) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    NavHost(navController, startDestination = AppRoutes.Main) {
        composable<AppRoutes.Auth.Login> { LoginScreen() }
        composable<AppRoutes.Main> { MainScreen() }
    }
}