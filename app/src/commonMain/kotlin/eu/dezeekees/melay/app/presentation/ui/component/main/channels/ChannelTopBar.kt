package eu.dezeekees.melay.app.presentation.ui.component.main.channels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel

@Composable
fun ChannelTopBar(
    uiState: MainScreenViewmodel.UIState,
    onMemberListToggleClick: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .shadow(
                elevation = 4.dp,
                shape = RectangleShape,
                clip = true
            )
            .background(MelayTheme.colorScheme.surface)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // left
        Row {  }

        Row(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            IconButton(
                onClick = onMemberListToggleClick,
                modifier = Modifier
                    .size(32.dp) // controls the button size
                    .padding(0.dp) // optional inner spacing
            ) {
                Icon(
                    imageVector = Icons.Default.Group,
                    contentDescription = "toggle members list",
                    tint = if (uiState.usersRowOpen) MelayTheme.colorScheme.onSurface
                    else MelayTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                )
            }

        }
    }
}