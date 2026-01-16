package eu.dezeekees.melay.app.presentation.ui.component.main.communities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.presentation.ui.theme.MelayTheme
import eu.dezeekees.melay.app.presentation.viewmodel.main.CreateChannelPopupViewModel
import eu.dezeekees.melay.app.presentation.viewmodel.main.CreateCommunityPopupViewModel

@Composable
fun CreateCommunityPopup(
    viewModel: CreateCommunityPopupViewModel,
    onCreateSuccess: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    if(uiState.popupOpen) {
        AlertDialog(
            onDismissRequest = viewModel::dismiss,
            title = { Text("Create community") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.communityName,
                        onValueChange = viewModel::onNameChanged,
                        label = { Text("Community name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        supportingText = {
                            Text(
                                uiState.nameError,
                                color = MelayTheme.colorScheme.error,
                            )
                        }
                    )

                    OutlinedTextField(
                        value = uiState.communityDescription,
                        onValueChange = viewModel::onDescriptionChanged,
                        label = { Text("Community description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        maxLines = 4,
                        supportingText = {
                            Text(
                                uiState.descriptionError,
                                color = MelayTheme.colorScheme.error,
                            )
                        }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.save(
                        onCreateSuccess
                    )
                }) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(onClick = viewModel::dismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}