package eu.dezeekees.melay.federation.service

import eu.dezeekees.melay.federation.client.FederationClient
import eu.dezeekees.melay.federation.config.FederationProperties
import eu.dezeekees.melay.federation.model.FederationMessage
import org.springframework.stereotype.Service

@Service
class FederationRouter(
	private val federationProperties: FederationProperties
) {
	private val peers = mutableMapOf<String, FederationClient>()

	fun initPeers() {
		federationProperties.peerList.forEach { domain ->
			val client = FederationClient("ws://${domain}/ws/federation")
			client.connect()
			peers[domain] = client
		}
	}

	fun broadcast(message: FederationMessage) {
		peers.values.forEach { it.send(message) }
	}
}
