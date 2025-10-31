package eu.dezeekees.melay.server.security

import eu.dezeekees.melay.server.security.JwtUtil
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

class JwtAuthenticationManager: ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val token = authentication.credentials as String
        val uuid = JwtUtil.validateToken(token)

        return if (uuid != null) {
            val auth = UsernamePasswordAuthenticationToken(uuid, null, emptyList())
            Mono.just(auth)
        } else Mono.empty()
    }
}