package eu.dezeekees.melay.federation.client

import eu.dezeekees.melay.federation.model.FederationMessage
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.messaging.simp.stomp.StompHeaders
import java.lang.reflect.Type

class FederationClient(private val url: String) {

    private val stompClient = WebSocketStompClient(StandardWebSocketClient()).apply {
        messageConverter = MappingJackson2MessageConverter()
    }

    private var session: StompSession? = null 

    fun connect() {
        stompClient.connectAsync(url, object : StompSessionHandlerAdapter() {
            override fun afterConnected(session: StompSession, headers: StompHeaders) {
                this@FederationClient.session = session
                println("Connected to federation peer at $url")
				
				session.subscribe("/topic/federation", object : StompFrameHandler {
					override fun getPayloadType(headers: StompHeaders): Type {
						return FederationMessage::class.java
					}

					override fun handleFrame(headers: StompHeaders, payload: Any?) {
						val message = payload as? FederationMessage
						if (message != null) {
							println("Received from peer: $message")
							// TODO: route locally
						}
					}
				})
            }
        })
    }

    fun send(message: FederationMessage) {
        session?.send("/app/federation", message)
    }
}

