package eu.dezeekees.melay.app.presentation.ui.component.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoginLogo(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
) {
    Box(
        modifier = modifier
    ) {
        Text(
            text = "Melay",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.align(Alignment.Center),
            color = color
        )
    }
}