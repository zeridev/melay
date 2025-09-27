package eu.dezeekees.melay.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "federation")
data class FederationProperties(
	val blacklist: List<String> = emptyList()
)
