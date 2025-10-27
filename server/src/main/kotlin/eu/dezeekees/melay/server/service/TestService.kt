package eu.dezeekees.melay.server.service

import eu.dezeekees.melay.server.dto.response.NumberResponse
import reactor.core.publisher.Flux
import eu.dezeekees.melay.server.service.TestService
import org.springframework.stereotype.Service

@Service
class TestService {
    fun getNumbers(limit: Int): Flux<NumberResponse> =
		Flux.range(1, limit)
			.map { NumberResponse(it) }
}
