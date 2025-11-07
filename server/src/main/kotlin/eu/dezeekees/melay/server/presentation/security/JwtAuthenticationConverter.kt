package eu.dezeekees.melay.server.presentation.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class JwtAuthenticationConverter : ServerAuthenticationConverter {
    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return Mono.justOrEmpty(exchange.request.headers.getFirst("Authorization"))
            .filter { it.startsWith("Bearer ") }
            .map { it.substring(7) }
            .map { UsernamePasswordAuthenticationToken(it, it) }
    }
}