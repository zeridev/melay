package eu.dezeekees.melay.app.presentation.ui.component.main.communities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import eu.dezeekees.melay.app.presentation.viewmodel.main.DiscoverCommunityPopupViewModel

@Composable
fun DiscoverCommunityPopup(
    viewModel: DiscoverCommunityPopupViewModel,
    onSuccessfulJoin: (() -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()

    if (!uiState.popupOpen) return

    Dialog(
        onDismissRequest = viewModel::dismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = MelayTheme.shapes.large,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Discover Communities",
                        style = MelayTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = viewModel::onSearchChanged,
                        singleLine = true,
                        placeholder = { Text("Search") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null)
                        },
                        modifier = Modifier
                            .widthIn(min = 160.dp, max = 220.dp)
                            .height(56.dp), // ← do not go smaller
                        textStyle = MelayTheme.typography.bodySmall,
                        shape = MelayTheme.shapes.large
                    )


                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(onClick = viewModel::dismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }

                HorizontalDivider()

                // Community list
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.sortedCommunities) { item ->
                        DiscoverCommunityPopupItem(
                            community = item.community,
                            joined = item.hasJoined,
                            onJoinClick = {
                                viewModel.joinCommunity(
                                    item.community.id,
                                    onSuccess = onSuccessfulJoin
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}