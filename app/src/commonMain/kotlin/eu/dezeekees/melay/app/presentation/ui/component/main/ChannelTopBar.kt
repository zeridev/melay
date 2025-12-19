package eu.dezeekees.melay.app.presentation.ui.component.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel

@Composable
fun ChannelTopBar(
    viewModel: MainScreenViewmodel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                onClick = { viewModel.toggleUsersRowOpen() },
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