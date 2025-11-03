package eu.dezeekees.melay.server.data.repository

import eu.dezeekees.melay.server.data.entity.UserEntity
import eu.dezeekees.melay.server.logic.model.User
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface UserR2dbcRepository: R2dbcRepository<User, Long> {
    fun findById(uuid: String): Mono<UserEntity>
    fun findByUsername(username: String): Mono<UserEntity>
    fun save(user: User): Mono<UserEntity>
}