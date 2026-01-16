package eu.dezeekees.melay.app.presentation.ui.component.main.channels

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.presentation.viewmodel.main.CreateChannelPopupViewModel

@Composable
fun CreateChannelPopup(
    selectedCommunity: CommunityResponse,
    viewModel: CreateChannelPopupViewModel,
    onCreateSuccess: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    if(uiState.popupOpen) {
        AlertDialog(
            onDismissRequest = viewModel::dismiss,
            title = { Text("Create channel") },
            text = {
                OutlinedTextField(
                    value = uiState.channelName,
                    onValueChange = viewModel::onNameChanged,
                    label = { Text("Channel name") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.save(
                        selectedCommunity,
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