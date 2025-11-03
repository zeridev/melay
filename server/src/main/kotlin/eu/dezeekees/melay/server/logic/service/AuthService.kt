package eu.dezeekees.melay.server.logic.service

import eu.dezeekees.melay.server.logic.dto.request.LoginRequest
import eu.dezeekees.melay.server.logic.dto.response.TokenResponse
import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.logic.exception.BadRequestException
import eu.dezeekees.melay.server.logic.util.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {
    fun login(request: LoginRequest): Mono<TokenResponse> = userRepository.findByUsername(request.username)
        .switchIfEmpty(
            Mono.error(BadRequestException("user does not exist or password is incorrect"))
        )
        .flatMap { user ->
            if (user.id == null) {
                Mono.error(BadRequestException("invalid user"))
            } else if (!passwordEncoder.matches(request.password, user.passwordHash)) {
                Mono.error(BadRequestException("user does not exist or password is incorrect"))
            } else {
                val token = JwtUtil.generateToken(user.id)
                Mono.just(TokenResponse(token))
            }
        }
}