package eu.dezeekees.melay.federation.server

import eu.dezeekees.melay.federation.model.FederationMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class FederationController {
	
	@MessageMapping("/federation")
	@SendTo("/topic/federation")
	fun handle(message: FederationMessage): FederationMessage {
		println("Received federation message: $message")
        // TODO: route to local community based on message.communityId
        return message
	}

}

