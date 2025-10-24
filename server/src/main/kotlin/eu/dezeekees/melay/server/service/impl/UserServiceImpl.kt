package eu.dezeekees.melay.server.service.impl

import eu.dezeekees.melay.server.dto.request.CreateUserRequest
import eu.dezeekees.melay.server.exception.BadRequestException
import eu.dezeekees.melay.server.exception.NotFoundException
import eu.dezeekees.melay.server.model.User
import eu.dezeekees.melay.server.repository.UserRepository
import eu.dezeekees.melay.server.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
): UserService {
    override fun create(user: CreateUserRequest): Mono<User> {
        return Mono.justOrEmpty(passwordEncoder.encode(user.password))
            .switchIfEmpty(Mono.error(BadRequestException("Password encoding failed")))
            .flatMap { encoded ->
                Mono.defer {
                    userRepository.findByUsername(user.username)
                        .flatMap<User> {
                            Mono.error(BadRequestException("User ${user.username} already exists"))
                        }
                        .switchIfEmpty (
                            userRepository.save(
                                User(
                                    username = user.username,
                                    displayName = user.username,
                                    passwordHash = encoded,
                                )
                            )
                        )
                }
            }
    }

    override fun getByUUID(uuid: String): Mono<User> {
        return userRepository.findById(uuid)
            .switchIfEmpty(Mono.error(NotFoundException("User $uuid not found")))
    }
}