package eu.dezeekees.melay.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.dezeekees.melay.app.presentation.ui.screen.LoginScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = AppRoutes.Auth.Login) {
        composable<AppRoutes.Auth.Login> { LoginScreen() }
    }
}