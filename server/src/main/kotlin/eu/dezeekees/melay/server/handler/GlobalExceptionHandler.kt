package eu.dezeekees.melay.server.handler

import eu.dezeekees.melay.server.exception.BadRequestException
import eu.dezeekees.melay.server.exception.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import reactor.core.publisher.Mono

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler
    fun handleNotFoundException(e: NotFoundException): Mono<ResponseEntity<Map<String, String>>> {
        val body = mapOf("error: " to e.message.orEmpty())
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(body))
    }

    @ExceptionHandler
    fun handleNotFoundException(e: BadRequestException): Mono<ResponseEntity<Map<String, String>>> {
        val body = mapOf("error: " to e.message.orEmpty())
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body))
    }

    @ExceptionHandler
    fun handleGenericException(e: Exception): Mono<ResponseEntity<Map<String, String>>> {
        val body = mapOf("error: " to "Internal Server Error")
        logger.error(e.message, e)
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body))
    }
}