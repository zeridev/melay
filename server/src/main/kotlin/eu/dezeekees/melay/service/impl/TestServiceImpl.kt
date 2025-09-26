package eu.dezeekees.melay.service.impl

import eu.dezeekees.melay.dto.response.NumberResponse
import reactor.core.publisher.Flux
import eu.dezeekees.melay.service.TestService
import org.springframework.stereotype.Service

@Service
class TestServiceImpl : TestService {
	
	override fun getNumbers(limit: Int): Flux<NumberResponse> = 
		Flux.range(1, limit)
			.map { NumberResponse(it) }
}
