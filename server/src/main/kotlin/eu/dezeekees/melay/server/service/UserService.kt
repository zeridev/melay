package eu.dezeekees.melay.server.service

import eu.dezeekees.melay.server.dto.request.CreateUserRequest
import eu.dezeekees.melay.server.exception.BadRequestException
import eu.dezeekees.melay.server.exception.NotFoundException
import eu.dezeekees.melay.server.mapper.CreateUserMapper
import eu.dezeekees.melay.server.model.User
import eu.dezeekees.melay.server.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun create(request: CreateUserRequest): Mono<User> {
        return Mono.justOrEmpty(passwordEncoder.encode(request.password))
            .switchIfEmpty(Mono.error(BadRequestException("Password encoding failed")))
            .flatMap { encoded ->
                val user = CreateUserMapper.fromRequest(request)
                user.passwordHash = encoded
                Mono.defer {
                    userRepository.findByUsername(user.username)
                        .flatMap<User> {
                            Mono.error(BadRequestException("User ${user.username} already exists"))
                        }
                        .switchIfEmpty (
                            userRepository.save(user)
                        )
                }
            }
    }

    fun getByUUID(uuid: String): Mono<User> {
        return userRepository.findById(uuid)
            .switchIfEmpty(Mono.error(NotFoundException("User $uuid not found")))
    }
}