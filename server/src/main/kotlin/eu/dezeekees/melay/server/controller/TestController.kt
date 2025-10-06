package eu.dezeekees.melay.server.controller

import eu.dezeekees.melay.server.dto.response.NumberResponse
import eu.dezeekees.melay.server.federation.model.FederationMessage
import eu.dezeekees.melay.server.federation.service.FederationRouter
import eu.dezeekees.melay.server.service.TestService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Flux
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

data class Message(
	val payload: String
)

@RestController
@RequestMapping("/test")
class TestController(
	private val testService: TestService,
	private val federationRouter: FederationRouter
) {
	@GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_NDJSON_VALUE])
	fun streamNumbers(): Flux<NumberResponse> =
		testService.getNumbers(100)

	@PostMapping
	fun sendMessage(@RequestBody message: Message): Mono<Void> {
		val federationMessage = FederationMessage(
			type = "test",
			communityId = "1",
			sender = "testsender@localhost:8090",
			content = message.payload 
		)

		federationRouter.broadcast(federationMessage)

		return Mono.empty()
	}
}
