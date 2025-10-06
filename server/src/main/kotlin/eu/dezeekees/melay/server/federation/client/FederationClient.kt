package eu.dezeekees.melay.server.federation.client

import eu.dezeekees.melay.server.federation.model.FederationMessage
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class FederationClient(
	private val requester: RSocketRequester
) {
	fun send(message: FederationMessage) : Mono<Void> {
		return requester
			.route("federation.send")
			.data(message)
			.send()
	}

	fun stream(): Flux<FederationMessage> {
		return requester
			.route("federation.stream")
			.retrieveFlux()
	}
}

