package eu.dezeekees.melay.app.presentation.ui.component.main.communities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityBarItem(
    community: CommunityResponse,
    onClick: () -> Unit,
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Below),
        tooltip = { PlainTooltip {
            Text(community.name)
        } },
        state = rememberTooltipState(
            isPersistent = true,
        )
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(MelayTheme.colorScheme.secondary)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center,
        ) {
            if(community.iconUrl.isNotBlank()) {
                AsyncImage(
                    model = community.iconUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = community.name.firstOrNull()?.uppercase() ?: "",
                    color = MelayTheme.colorScheme.onSecondary,
                )
            }
        }
    }
}