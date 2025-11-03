package eu.dezeekees.melay.server.presentation.federation.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "federation")
data class FederationProperties(
	val peerList: List<String> = emptyList()
)
