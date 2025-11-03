package eu.dezeekees.melay.server.logic.repository

import eu.dezeekees.melay.server.logic.model.User
import reactor.core.publisher.Mono

interface UserRepository {
    fun findById(id: String): Mono<User>
    fun findByUsername(username: String): Mono<User>
    fun save(user: User): Mono<User>
}