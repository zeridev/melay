package eu.dezeekees.melay.federation.init

import eu.dezeekees.melay.federation.service.FederationRouter
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class FederationClientStartupRunner(
	private val federationRouter: FederationRouter
): ApplicationRunner {
	override fun run(args: ApplicationArguments) {
		federationRouter.initPeers()
	}
} 
