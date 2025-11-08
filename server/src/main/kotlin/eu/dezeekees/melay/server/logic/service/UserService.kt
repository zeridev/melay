package eu.dezeekees.melay.server.logic.service

import eu.dezeekees.melay.server.logic.exception.BadRequestException
import eu.dezeekees.melay.server.logic.exception.NotFoundException
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.logic.util.PasswordUtil
import java.util.*

class UserService(
    private val userRepository: UserRepository,
) {

    suspend fun create(username: String, password: String): User {
        val existing = userRepository.findByUsername(username)
        if(existing != null) throw BadRequestException("User with username: $username already exists")

        val user = User(
            username = username,
            displayName = username,
            passwordHash = PasswordUtil.hash(password)
        )

        return userRepository.save(user)
    }

    suspend fun getById(id: String): User {
        val uuid = try {
            UUID.fromString(id)
        } catch (_: Exception) {
            throw BadRequestException("Invalid UUID format: $id")
        }

        val user = userRepository.findById(uuid)

        if(user != null) return user

        throw NotFoundException("User not found")
    }
}