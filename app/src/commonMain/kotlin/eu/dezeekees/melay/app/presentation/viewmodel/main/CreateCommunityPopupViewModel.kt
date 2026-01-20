package eu.dezeekees.melay.app.presentation.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.dezeekees.melay.app.logic.error.NetworkError
import eu.dezeekees.melay.app.logic.error.NetworkException
import eu.dezeekees.melay.app.logic.error.onError
import eu.dezeekees.melay.app.logic.error.onSuccess
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.service.ChannelService
import eu.dezeekees.melay.app.logic.service.CommunityService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CreateCommunityPopupViewModel(
    private val communityService: CommunityService,
): ViewModel() {
    data class UIState(
        val popupOpen: Boolean = false,
        val communityName: String = "",
        val communityDescription: String = "",
        val nameError: String = "",
        val descriptionError: String = "",
    )
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(communityName = name)
    }

    fun onDescriptionChanged(description: String) {
        _uiState.value = _uiState.value.copy(communityDescription = description)
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
        onSuccess: (() -> Unit)? = null
    ){
        viewModelScope.launch {
            communityService.createCommunity(
                _uiState.value.communityName,
                _uiState.value.communityDescription,
            ).onSuccess {
                _uiState.value = _uiState.value.copy(
                    popupOpen = false
                )
                onSuccess?.invoke()
            }.onError {
                val error = it as NetworkError
                _uiState.value = _uiState.value.copy(
                    nameError = error.getReasonForField("name"),
                    descriptionError = error.getReasonForField("description")
                )
            }
        }
    }
}