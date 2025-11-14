package eu.dezeekees.melay.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.dezeekees.melay.app.presentation.ui.screen.LoginScreen
import eu.dezeekees.melay.app.presentation.ui.screen.MainScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavigationHandler(navController)

    NavHost(navController, startDestination = AppRoutes.Auth.Login) {
        composable<AppRoutes.Auth.Login> { LoginScreen() }
        composable<AppRoutes.Main> { MainScreen() }
    }
}