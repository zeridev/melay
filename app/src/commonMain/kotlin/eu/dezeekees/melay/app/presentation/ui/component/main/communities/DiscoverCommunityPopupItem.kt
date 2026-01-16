package eu.dezeekees.melay.app.presentation.ui.component.main.communities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme

@Composable
fun DiscoverCommunityPopupItem(
    community: CommunityResponse,
    joined: Boolean,
    onJoinClick: () -> Unit
) {
    Surface(
        shape = MelayTheme.shapes.medium,
        color = MelayTheme.colorScheme.background,
        border = BorderStroke(
            1.dp,
            MelayTheme.colorScheme.outline
        ),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = community.name,
                style = MelayTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onJoinClick,
                enabled = !joined
            ) {
                Text(if (joined) "Joined" else "Join")
            }
        }
    }
}
