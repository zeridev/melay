package eu.dezeekees.melay.controller

import eu.dezeekees.melay.dto.response.NumberResponse
import eu.dezeekees.melay.service.TestService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Flux
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
	private val testService: TestService
) {
	@GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_NDJSON_VALUE])
	fun streamNumbers(): Flux<NumberResponse> =
		testService.getNumbers(100)
}
