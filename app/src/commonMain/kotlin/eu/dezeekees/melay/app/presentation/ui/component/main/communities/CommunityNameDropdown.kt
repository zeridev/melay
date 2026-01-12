package eu.dezeekees.melay.app.presentation.ui.component.main.communities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme

@Composable
fun ServerNameDropdown(
    selectedCommunity: CommunityResponse,
    onCreateChannelClick: () -> Unit,
    onLeaveCommunityClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { expanded = true }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = selectedCommunity.name,
                style = MelayTheme.typography.titleMedium,
            )

            Icon(
                modifier = Modifier
                    .rotate(if (expanded) 0f else 180f),
                imageVector = Icons.Filled.ExpandLess,
                contentDescription = "Open community dropdown",
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            shape = MaterialTheme.shapes.medium,
        ) {
            ServerNameDropdownItem(
                text = "Create Channel",
                onClick = {
                    expanded = false
                    onCreateChannelClick()
                },
                leadingIcon = Icons.Outlined.AddCircle,
            )

            ServerNameDropdownItem(
                text = "Leave Community",
                onClick = {
                    expanded = false
                    onLeaveCommunityClick()
                },
                leadingIcon = Icons.AutoMirrored.Default.ExitToApp,
                textColor = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@Composable
fun ServerNameDropdownItem(
    text: String,
    onClick: () -> Unit,
    leadingIcon: ImageVector,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    DropdownMenuItem(
        modifier = Modifier
            .height(36.dp),
        leadingIcon = { Icon(
            leadingIcon,
            contentDescription = text,
            tint = textColor,
            modifier = Modifier
                .size(22.dp)
        )},
        text = { Text(
            text = text,
            style = MelayTheme.typography.titleSmall,
            color = textColor,
        ) },
        onClick = onClick
    )
}