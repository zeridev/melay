package eu.dezeekees.melay.server.data.repository.adapeter

import eu.dezeekees.melay.server.data.entity.UserEntity
import eu.dezeekees.melay.server.data.entity.toUser
import eu.dezeekees.melay.server.data.repository.UserR2dbcRepository
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserRepositoryImpl(
    private val r2dbcRepository: UserR2dbcRepository
): UserRepository {
    override fun findById(id: String): Mono<User> =
        r2dbcRepository.findById(id)
            .map(UserEntity::toUser)

    override fun findByUsername(username: String): Mono<User> =
        r2dbcRepository.findByUsername(username)
            .map(UserEntity::toUser)

    override fun save(user: User): Mono<User> =
        r2dbcRepository.save(user)
            .map(UserEntity::toUser)
}