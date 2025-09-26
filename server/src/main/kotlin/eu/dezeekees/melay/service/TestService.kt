package eu.dezeekees.melay.service

import eu.dezeekees.melay.dto.response.NumberResponse
import reactor.core.publisher.Flux

interface TestService {
	fun getNumbers(limit: Int): Flux<NumberResponse>
}
