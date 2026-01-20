package eu.dezeekees.melay.app.presentation.ui.component.main.communities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CommunityBar(
    uiState: MainScreenViewmodel.UIState,
    onCommunityBarItemClick: (UUID) -> Unit,
    onCreateCommunityClick: () -> Unit,
    onDiscoverCommunityClick: () -> Unit,
) {
    val communities = uiState.userCommunities

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        communities.forEach { community ->
            CommunityBarItem(
                community,
                onClick = { onCommunityBarItemClick(community.id) },
            )
        }

        TooltipBox(
            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Below),
            tooltip = { PlainTooltip {
                Text("Create new community")
            } },
            state = rememberTooltipState(
                isPersistent = true
            )
        ) {
            FilledIconButton(
                onClick = onCreateCommunityClick,
                modifier = Modifier.size(42.dp)
            ) { Icon(Icons.Default.Add, contentDescription = "create community") }
        }

        TooltipBox(
            positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Below),
            tooltip = { PlainTooltip {
                Text("Discover Communities")
            } },
            state = rememberTooltipState(
                isPersistent = true
            )
        ) {
            FilledIconButton(
                onClick = onDiscoverCommunityClick,
                modifier = Modifier.size(42.dp)
            ) { Icon(Icons.Default.Explore, contentDescription = "discover community") }
        }
    }
}