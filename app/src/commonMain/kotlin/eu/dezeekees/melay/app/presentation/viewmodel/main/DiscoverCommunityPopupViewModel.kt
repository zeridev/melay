package eu.dezeekees.melay.app.presentation.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.dezeekees.melay.app.logic.error.onSuccess
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.service.CommunityService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class DiscoverCommunityPopupViewModel(
    private val communityService: CommunityService,
): ViewModel() {
    data class UIState(
        val popupOpen: Boolean = false,
        val communities: List<DiscoverCommunity> = emptyList(),
        val searchQuery: String = ""
    ) {
        val sortedCommunities: List<DiscoverCommunity>
            get() = communities
                .filter { it.community.name.contains(searchQuery, ignoreCase = true) }
                .sortedBy { it.community.name.lowercase() }
    }

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    fun onSearchChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun loadCommunities(joinedCommunities: List<CommunityResponse>) {
        viewModelScope.launch {
            communityService.getAllCommunities()
                .onSuccess { communities ->
                    val discoverList = communities.map { community ->
                        DiscoverCommunity(
                            community = community,
                            hasJoined = joinedCommunities.any { it.id == community.id }
                        )
                    }

                    _uiState.value = _uiState.value.copy(
                        communities = discoverList
                    )
                }
        }
    }

    fun open(
        joinedCommunities: List<CommunityResponse>,
    ) {
        _uiState.value = _uiState.value.copy(
            popupOpen = true
        )
        loadCommunities(joinedCommunities)
    }

    fun dismiss() {
        _uiState.value = _uiState.value.copy(
            popupOpen = false
        )
    }

    fun joinCommunity(
        communityId: UUID,
        onSuccess: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            communityService.joinCommunity(communityId)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        communities = _uiState.value.communities.map { item ->
                            if (item.community.id == communityId) {
                                item.copy(hasJoined = true)
                            } else {
                                item
                            }
                        }
                    )

                    onSuccess?.invoke()
                }
        }
    }
}

data class DiscoverCommunity(
    val hasJoined: Boolean = false,
    val community: CommunityResponse,
)