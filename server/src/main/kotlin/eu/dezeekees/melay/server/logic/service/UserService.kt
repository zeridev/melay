package eu.dezeekees.melay.server.logic.service

import eu.dezeekees.melay.server.logic.exception.BadRequestException
import eu.dezeekees.melay.server.logic.exception.NotFoundException
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun create(username: String, password: String): Mono<User> {
        return Mono.justOrEmpty(passwordEncoder.encode(password))
            .switchIfEmpty(Mono.error(BadRequestException("Password encoding failed")))
            .flatMap { encoded ->
                val user = User(
                    username = username,
                    displayName = username,
                    passwordHash = encoded,
                )

                Mono.defer {
                    userRepository.findByUsername(user.username)
                        .flatMap {
                            Mono.error<User>(BadRequestException("User ${user.username} already exists"))
                        }
                        .switchIfEmpty (
                            userRepository.save(user)
                        )
                }
            }
    }

    fun getById(id: String): Mono<User> =
        Mono.fromCallable { UUID.fromString(id) }
            .onErrorMap { BadRequestException("Invalid UUID format: $id") }
            .flatMap { uuid ->
                userRepository.findById(uuid)
                    .switchIfEmpty(Mono.error(NotFoundException("User $id not found")))
            }
}