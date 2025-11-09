package eu.dezeekees.melay.app.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

@Composable
expect fun getPlatformColorScheme(darkMode: Boolean = false): ColorScheme