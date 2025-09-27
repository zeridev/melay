package eu.dezeekees.melay.websocket.handler

import eu.dezeekees.melay.config.FederationProperties
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.CloseStatus
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class FederationWebsocketHandler(
    private val federationProperties: FederationProperties
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        println("New WebSocket connection attempt")
		
		return session.handshakeInfo.principal
			.switchIfEmpty(
				return Mono.defer {
					println("Rejecting connection: no client certificate")
					session.close(CloseStatus(4001, "Client certificate required"))
				}
			)
			.flatMap { principal ->
				if (federationProperties.blacklist.any { it.equals(principal.name, ignoreCase = true) }) {
					println("Rejecting connection: blacklisted CN=${principal.name}")
					session.close(CloseStatus(4003, "Blacklisted"))
				} else {
					println("Accepted connection: CN=${principal.name}")
					session.receive()
						.doOnNext { msg -> println("Received message: ${msg.payloadAsText}") }
						.then()
				}
			}
    }
}

