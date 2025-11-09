package eu.dezeekees.melay.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import eu.dezeekees.melay.app.di.sharedModule
import eu.dezeekees.melay.app.presentation.navigation.AppNavHost
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    MelayTheme {
        KoinApplication({
            modules(sharedModule)
        }) {
            val navController = rememberNavController()
            AppNavHost(navController)
        }
    }
}
