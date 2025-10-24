package eu.dezeekees.melay.server.repository

import eu.dezeekees.melay.server.model.User
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface UserRepository: R2dbcRepository<User, Long> {
    fun findById(uuid: String): Mono<User>
    fun findByUsername(username: String): Mono<User>
}