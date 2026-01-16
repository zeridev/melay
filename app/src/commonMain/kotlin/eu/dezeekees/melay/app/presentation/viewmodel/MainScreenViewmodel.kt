package eu.dezeekees.melay.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.dezeekees.melay.app.logic.error.onError
import eu.dezeekees.melay.app.logic.error.onSuccess
import eu.dezeekees.melay.app.logic.`interface`.IRSocketClient
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.model.user.UserResponse
import eu.dezeekees.melay.app.logic.service.CommunityService
import eu.dezeekees.melay.app.logic.service.UserService
import eu.dezeekees.melay.common.rsocket.ConfiguredProtoBuf
import io.rsocket.kotlin.payload.Payload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
class MainScreenViewmodel(
    private val rSocketClient: IRSocketClient,
    private val userService: UserService,
    private val communityService: CommunityService,
): ViewModel() {
    @OptIn(ExperimentalSerializationApi::class)
    private val proto = ConfiguredProtoBuf
    private val outgoing = MutableSharedFlow<Payload>()
    lateinit var stream: Flow<Payload>

    data class UIState(
        val usersRowOpen: Boolean = false,
        val user: UserResponse? = null,
        val userCommunities: List<CommunityResponse> = emptyList(),
        val selectedCommunityId: UUID? = null,
    )

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState

    val selectedCommunity: CommunityResponse
        get() = uiState.value.userCommunities.firstOrNull { it.id == uiState.value.selectedCommunityId } ?: CommunityResponse(
            UUID.randomUUID()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart { initialize() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    private fun initialize() {
        viewModelScope.launch {
            _isLoading.value = true

            userService.getMe()
                .onSuccess { user ->
                    _uiState.value = _uiState.value.copy(
                        user = user,
                    )
                }
                .onError { return@launch }

            userService.getMyCommunities()
                .onSuccess { communities ->
                    _uiState.value = _uiState.value.copy(
                        userCommunities = communities,
                        selectedCommunityId = communities.lastOrNull()?.id
                    )
                }

            getCommunities()
        }
    }

    private suspend fun getCommunities() {
        userService.getMyCommunities()
            .onSuccess { communities ->
                _uiState.value = _uiState.value.copy(
                    userCommunities = communities,
                    selectedCommunityId = communities.lastOrNull()?.id
                )
            }
    }

    fun reloadAllCommunities() {
        viewModelScope.launch {
            getCommunities()
        }
    }

    fun toggleUsersRowOpen() {
        _uiState.value = _uiState.value.copy(
            usersRowOpen = !_uiState.value.usersRowOpen
        )
    }

    fun setSelectedCommunity(communityId: UUID) {
        _uiState.value = _uiState.value.copy(
            selectedCommunityId = communityId,
        )
    }

    fun leaveSelectedCommunity() {
        viewModelScope.launch {
            communityService.leaveCommunity(selectedCommunity.id).onSuccess {
                reloadAllCommunities()
            }
        }
    }

    fun deleteSelectedCommunity() {
        viewModelScope.launch {
            communityService.deleteCommunity(selectedCommunity.id).onSuccess {
                reloadAllCommunities()
            }
        }
    }

    init {
//        viewModelScope.launch {
//            if (!rSocketClient.isConnected) {
//                rSocketClient.connect("ws://127.0.0.1:8080${Routes.Socket.MelayClient.CONNECTION_ROUTE}")
//            }
//
//            val rSocket = rSocketClient.getRSocket()
//
//            stream = rSocket.requestChannel(
//                Payload(route = "stream.{community_id:1}.{channel_id:1}.messages"),
//                outgoing
//            )
//
//            stream.collect { payload ->
//                println("Received payload: ${payload.data.readText()}")
//            }
//        }
    }

}