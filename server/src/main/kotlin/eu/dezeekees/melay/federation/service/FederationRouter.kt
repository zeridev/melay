package eu.dezeekees.melay.federation.service

import eu.dezeekees.melay.federation.client.FederationClient
import eu.dezeekees.melay.federation.config.FederationProperties
import eu.dezeekees.melay.federation.model.FederationMessage
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.doOnError
import reactor.kotlin.core.publisher.toMono

@Service
class FederationRouter(
	private val federationProperties: FederationProperties,
	private val requestBuilder: RSocketRequester.Builder
) {
	private val peers = mutableMapOf<String, FederationClient>()

	fun initPeers() {
		federationProperties.peerList.forEach { domain ->
			val parts = domain.split(":")
			val host = parts[0]
			val port = parts.getOrElse(1) { "7000" }.toInt()

			requestBuilder
				.tcp(host, port)
				.toMono()
				.doOnNext { requester ->
					val client = FederationClient(requester)
					peers[domain] = client

					client.stream().subscribe { msg ->
						println(msg)	
					}

					println("connected to ${domain}")
				}
				.doOnError { error ->
					println("Failed to connect to $domain error: ${error.message}")
				}
				.subscribe()
		}
	}

	fun broadcast(message: FederationMessage) =
		peers.values.forEach { it.send(message).subscribe() }
}
