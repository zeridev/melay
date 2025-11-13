package eu.dezeekees.melay.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import eu.dezeekees.melay.app.di.platformModule
import eu.dezeekees.melay.app.di.sharedModule
import eu.dezeekees.melay.app.presentation.navigation.AppNavHost
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration


@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    MelayTheme {
        KoinMultiplatformApplication(koinConfiguration {
            modules(sharedModule, platformModule)
        }) {
            val navController = rememberNavController()
            AppNavHost(navController)
        }
    }
}
