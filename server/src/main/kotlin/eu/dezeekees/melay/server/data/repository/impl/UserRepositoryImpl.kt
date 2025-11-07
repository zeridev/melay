package eu.dezeekees.melay.server.data.repository.impl

import eu.dezeekees.melay.server.data.UserMapper
import eu.dezeekees.melay.server.data.repository.UserR2dbcRepository
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class UserRepositoryImpl(
    private val r2dbcRepository: UserR2dbcRepository
): UserRepository {
    override fun findById(id: UUID): Mono<User> =
        r2dbcRepository.findById(id)
            .map(UserMapper::toUser)

    override fun findByUsername(username: String): Mono<User> =
        r2dbcRepository.findByUsername(username)
            .map(UserMapper::toUser)

    override fun save(user: User): Mono<User> =
        r2dbcRepository.save(UserMapper.toEntity(user))
            .map(UserMapper::toUser)
}