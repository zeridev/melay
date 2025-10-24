package eu.dezeekees.melay.server.service

import eu.dezeekees.melay.server.dto.request.CreateUserRequest
import eu.dezeekees.melay.server.model.User
import reactor.core.publisher.Mono

interface UserService {
    fun create(user: CreateUserRequest): Mono<User>
    fun getByUUID(uuid: String): Mono<User>
}