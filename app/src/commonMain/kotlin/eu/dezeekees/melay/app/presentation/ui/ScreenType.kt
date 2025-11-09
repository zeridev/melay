package eu.dezeekees.melay.app.presentation.ui

import androidx.window.core.layout.WindowSizeClass

private object Breakpoints {
    const val LARGE = 600
}

enum class ScreenType {
    DESKTOP,
    MOBILE;

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): ScreenType {
            return when {
                windowSizeClass.isWidthAtLeastBreakpoint(Breakpoints.LARGE) -> DESKTOP
                else -> MOBILE
            }
        }
    }
}