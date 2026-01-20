package eu.dezeekees.melay.app.presentation.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.dezeekees.melay.app.logic.error.onSuccess
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.service.ChannelService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CreateChannelPopupViewModel(
    private val channelService: ChannelService,
): ViewModel() {
    data class UIState(
        val popupOpen: Boolean = false,
        val channelName: String = ""
    )
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(channelName = name)
    }

    fun open() {
        _uiState.value = _uiState.value.copy(
            popupOpen = true
        )
    }

    fun dismiss() {
        _uiState.value = _uiState.value.copy(
            popupOpen = false
        )
    }

    fun save(
        selectedCommunity: CommunityResponse,
        unSuccess: (() -> Unit)? = null
    ){
        viewModelScope.launch {
            channelService.createChannel(
                _uiState.value.channelName,
                selectedCommunity.id
            ).onSuccess {
                _uiState.value = _uiState.value.copy(
                    popupOpen = false
                )
                unSuccess?.invoke()
            }
        }
    }

    fun delete(
        channelId: UUID,
        onSuccess: (() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            channelService.deleteChannel(channelId).onSuccess {
                onSuccess?.invoke()
            }
        }
    }

}