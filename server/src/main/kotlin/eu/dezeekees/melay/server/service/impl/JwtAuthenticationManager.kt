package eu.dezeekees.melay.server.service.impl

import eu.dezeekees.melay.server.util.JwtUtil
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class JwtAuthenticationManager: ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val token = authentication.credentials as String
        val uuid = JwtUtil.validateToken(token)

        return if (uuid != null) {
            val auth = UsernamePasswordAuthenticationToken(uuid, null)
            Mono.just(auth)
        } else Mono.empty()
    }
}