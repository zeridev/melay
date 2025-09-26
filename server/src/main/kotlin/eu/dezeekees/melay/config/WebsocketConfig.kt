package eu.dezeekees.melay.config

import eu.dezeekees.melay.websocket.handler.ChatWebSocketHandler
import eu.dezeekees.melay.websocket.handler.FederationWebsocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

@Configuration
class WebSocketConfig(
    private val chatHandler: ChatWebSocketHandler,
    private val federationHandler: FederationWebsocketHandler
) {
    @Bean
    fun webSocketMapping(): HandlerMapping {
        val map = mapOf(
            "/ws/chat" to chatHandler,
            "/ws/federation" to federationHandler
        )

        return SimpleUrlHandlerMapping().apply {
            urlMap = map
            order = -1
        }
    }

    @Bean
    fun handlerAdapter() = WebSocketHandlerAdapter()
}

