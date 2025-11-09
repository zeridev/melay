package eu.dezeekees.melay.app.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode

@Composable
fun MelayTheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = getPlatformColorScheme(darkTheme)

    MaterialTheme(colorScheme = colorScheme) {
        content()
    }
}

object MelayTheme {
    val shapes: Shapes
        @Composable @ReadOnlyComposable get() = MaterialTheme.shapes

    val typography: Typography
        @Composable @ReadOnlyComposable get() = MaterialTheme.typography

    val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme

    object gradientBrush {
        val primary: Brush
            @Composable @ReadOnlyComposable get() {
                return Brush.linearGradient(
                    listOf(
                        colorScheme.primary,
                        colorScheme.tertiary,
                    )
                )
            }
    }
}