package eu.dezeekees.melay.server.service

import eu.dezeekees.melay.server.dto.response.NumberResponse
import reactor.core.publisher.Flux

interface TestService {
	fun getNumbers(limit: Int): Flux<NumberResponse>
}
