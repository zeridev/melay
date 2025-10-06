package eu.dezeekees.melay.server.federation.server

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.federation.model.FederationMessage
import org.springframework.stereotype.Controller
import reactor.core.publisher.Sinks
import org.springframework.messaging.handler.annotation.MessageMapping
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class FederationController {
	
	private val sink = Sinks.many().multicast().onBackpressureBuffer<FederationMessage>()

	@MessageMapping(Routes.Ws.Federation.Send.NAME)
	fun recieve(message: FederationMessage): Mono<Void> {
		println("recieved message ${message.content}")
		sink.tryEmitNext(message)
		return Mono.empty()
	}

    @MessageMapping(Routes.Ws.Federation.Stream.NAME)
        fun stream(): Flux<FederationMessage> =
            sink.asFlux()
}

