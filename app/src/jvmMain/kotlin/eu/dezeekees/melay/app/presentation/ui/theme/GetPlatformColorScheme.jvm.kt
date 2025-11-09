package eu.dezeekees.melay.app.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
actual fun getPlatformColorScheme(darkMode: Boolean): ColorScheme {
    return if (darkMode) darkColorScheme() else lightColorScheme()
}