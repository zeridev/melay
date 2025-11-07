package eu.dezeekees.melay.server.logic.service

import eu.dezeekees.melay.server.presentation.dto.auth.LoginRequest
import eu.dezeekees.melay.server.presentation.dto.auth.TokenResponse
import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.logic.exception.BadRequestException
import eu.dezeekees.melay.server.logic.model.Token
import eu.dezeekees.melay.server.logic.util.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
) {
 
}