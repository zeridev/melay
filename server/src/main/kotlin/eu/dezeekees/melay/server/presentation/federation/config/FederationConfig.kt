package eu.dezeekees.melay.server.presentation.federation.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(FederationProperties::class)
class FederationConfig

