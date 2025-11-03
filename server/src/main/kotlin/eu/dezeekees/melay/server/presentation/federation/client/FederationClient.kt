package eu.dezeekees.melay.server.presentation.federation.client

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.presentation.federation.model.FederationMessage
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class FederationClient(
	private val requester: RSocketRequester
) {
	fun send(message: FederationMessage) : Mono<Void> {
		return requester
			.route(Routes.Ws.Federation.Send.NAME)
			.data(message)
			.send()
	}

	fun stream(): Flux<FederationMessage> {
		return requester
			.route(Routes.Ws.Federation.Stream.NAME)
			.retrieveFlux()
	}
}

