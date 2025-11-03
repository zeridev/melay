package eu.dezeekees.melay.server.logic.service

import eu.dezeekees.melay.server.logic.dto.request.CreateUserRequest
import eu.dezeekees.melay.server.logic.dto.response.UserResponse
import eu.dezeekees.melay.server.logic.exception.BadRequestException
import eu.dezeekees.melay.server.logic.exception.NotFoundException
import eu.dezeekees.melay.server.logic.mapper.toResponse
import eu.dezeekees.melay.server.logic.mapper.toUser
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun create(request: CreateUserRequest): Mono<UserResponse> {
        return Mono.justOrEmpty(passwordEncoder.encode(request.password))
            .switchIfEmpty(Mono.error(BadRequestException("Password encoding failed")))
            .flatMap { encoded ->
                val user = request.toUser()
                user.passwordHash = encoded
                Mono.defer {
                    userRepository.findByUsername(user.username)
                        .flatMap<User> {
                            Mono.error(BadRequestException("User ${user.username} already exists"))
                        }
                        .switchIfEmpty (
                            userRepository.save(user)
                        )
                }.map(User::toResponse)
            }
    }

    fun getById(id: String): Mono<UserResponse> {
        return userRepository.findById(id)
            .map(User::toResponse)
            .switchIfEmpty(Mono.error(NotFoundException("User $id not found")))
    }
}