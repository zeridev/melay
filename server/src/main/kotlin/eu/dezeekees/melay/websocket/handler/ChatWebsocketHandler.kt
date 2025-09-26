package eu.dezeekees.melay.websocket.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class ChatWebSocketHandler : WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> {
        val input = session.receive()
            .map { it.payloadAsText }
            .doOnNext { println("Received chat message: $it") }

        val output = input.map { session.textMessage("Echo: $it") }

        return session.send(output)
    }
}
