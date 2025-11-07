package eu.dezeekees.melay.server.logic.repository

import eu.dezeekees.melay.server.logic.model.User
import java.util.*

interface UserRepository {
    suspend fun findById(id: UUID): User?
    suspend fun findByUsername(username: String): User?
    suspend fun save(user: User): User
}