package eu.dezeekees.melay.app.presentation.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.dezeekees.melay.app.logic.error.onError
import eu.dezeekees.melay.app.logic.error.onSuccess
import eu.dezeekees.melay.app.logic.model.channel.ChannelResponse
import eu.dezeekees.melay.app.logic.model.community.CommunityResponse
import eu.dezeekees.melay.app.logic.model.message.CreateMessageRequest
import eu.dezeekees.melay.app.logic.model.message.MessageResponse
import eu.dezeekees.melay.app.logic.model.user.UserResponse
import eu.dezeekees.melay.app.logic.service.CommunityService
import eu.dezeekees.melay.app.logic.service.MessageService
import eu.dezeekees.melay.app.logic.service.UserService
import kotlinx.coroutines.Job
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
    private val userService: UserService,
    private val communityService: CommunityService,
    private val messageService: MessageService,
): ViewModel() {
    @OptIn(ExperimentalSerializationApi::class)

    data class UIState(
        val usersRowOpen: Boolean = false,
        val user: UserResponse? = null,
        val userCommunities: List<CommunityResponse> = emptyList(),
        val selectedCommunityId: UUID? = null,
        val selectedChannelId: UUID? = null,
        val channelMessages: List<MessageResponse> = emptyList(),
        val chatInputText: TextFieldValue = TextFieldValue(""),
    )

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState
    val selectedCommunity: CommunityResponse
        get() = uiState.value.userCommunities.firstOrNull { it.id == uiState.value.selectedCommunityId } ?: CommunityResponse(
            UUID.randomUUID()
        )
    val selectedChannel: ChannelResponse
        get() = selectedCommunity.channels.firstOrNull { it.id == uiState.value.selectedChannelId } ?: ChannelResponse(
            UUID.randomUUID()
        )
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart { initialize() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)
    private var currentChannelJob: Job? = null

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

            getCommunities()
        }
    }

    private suspend fun getCommunities() {
        userService.getMyCommunities()
            .onSuccess { communities ->
                _uiState.value = _uiState.value.copy(
                    userCommunities = communities,
                    selectedCommunityId = communities.firstOrNull()?.id,
                )

                val channelId = communities.firstOrNull()?.channels?.firstOrNull()?.id
                setSelectedChannel(channelId!!)
            }
    }

    fun reloadAllCommunities() {
        viewModelScope.launch {
            getCommunities()
        }
    }

    private suspend fun getSelectedChannelMessages() {
        messageService.getAllForChannel(selectedChannel.id)
            .onSuccess { messages ->
                _uiState.value = _uiState.value.copy(
                    channelMessages = messages.sortedByDescending { it.createdAt },
                )
            }
    }

    fun setSelectedChannel(channelId: UUID) {
        if(channelId != selectedChannel.id) {
            _uiState.value = _uiState.value.copy(
                selectedChannelId = channelId
            )

            viewModelScope.launch {
                getSelectedChannelMessages()
                startMessageStream(channelId)
            }
        }
    }

    private fun startMessageStream(channelId: UUID) {
        currentChannelJob?.cancel() // cancel previous stream if any

        currentChannelJob = viewModelScope.launch {
            messageService.stream(channelId) // stream from RSocket
                .collect { message ->
                    // append new messages
                    _uiState.value = _uiState.value.copy(
                        channelMessages = _uiState.value.channelMessages.toMutableList().apply { add(0, message) }
                    )
                }
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

    fun onChatInputTextChange(text: TextFieldValue) {
        _uiState.value = _uiState.value.copy(
            chatInputText = text
        )
    }

    fun sendMessage() {
        viewModelScope.launch {
            messageService.createMessage(CreateMessageRequest(
                channelId = selectedChannel.id,
                content = uiState.value.chatInputText.text
            )).onSuccess {
                _uiState.value = _uiState.value.copy(
                    chatInputText = TextFieldValue()
                )
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