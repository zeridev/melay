package eu.dezeekees.melay.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.dezeekees.melay.app.logic.`interface`.IRSocketClient
import eu.dezeekees.melay.app.logic.model.auth.Token
import eu.dezeekees.melay.app.logic.service.AuthService
import eu.dezeekees.melay.app.presentation.navigation.AppRoutes
import eu.dezeekees.melay.app.presentation.navigation.NavEvent
import eu.dezeekees.melay.app.presentation.navigation.NavigationManager
import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.common.rsocket.ConfiguredProtoBuf
import eu.dezeekees.melay.common.rsocket.Payload
import io.ktor.utils.io.*
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

@OptIn(ExperimentalSerializationApi::class)
class MainScreenViewmodel(
    private val rSocketClient: IRSocketClient,
    private val authService: AuthService,
    private val navigationManager: NavigationManager

): ViewModel() {
    @OptIn(ExperimentalSerializationApi::class)
    private val proto = ConfiguredProtoBuf
    private val outgoing = MutableSharedFlow<Payload>()
    lateinit var stream: Flow<Payload>

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
        .onStart { initialize() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    private val _token = MutableStateFlow<Token?>(null)
    val token: StateFlow<Token?> = _token


    private fun initialize() {
        viewModelScope.launch {
            _isLoading.value = true

            if(!authService.tokenIsSet()) {
                navigationManager.navigate(NavEvent.ToRouteClearingBackstack(AppRoutes.Auth.Login))
                return@launch
            }

            _token.value = authService.getToken()
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