package eu.dezeekees.melay.server.service

import eu.dezeekees.melay.server.dto.request.LoginRequest
import eu.dezeekees.melay.server.dto.response.TokenResponse
import eu.dezeekees.melay.server.exception.BadRequestException
import eu.dezeekees.melay.server.data.repository.UserRepository
import eu.dezeekees.melay.server.security.JwtUtil
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
            if(user.id == null) {
                Mono.error(BadRequestException("invalid user"))
            } else if(!passwordEncoder.matches(request.password, user.passwordHash)) {
                Mono.error(BadRequestException("user does not exist or password is incorrect"))
            } else {
                val token = JwtUtil.generateToken(user.id)
                Mono.just(TokenResponse(token))
            }
        }
}