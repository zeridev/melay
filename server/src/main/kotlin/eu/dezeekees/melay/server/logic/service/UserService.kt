package eu.dezeekees.melay.server.logic.service

import de.mkammerer.argon2.Argon2Factory
import eu.dezeekees.melay.server.logic.exception.BadRequestException
import eu.dezeekees.melay.server.logic.exception.NotFoundException
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserRepository
import java.util.*

class UserService(
    private val userRepository: UserRepository,
) {
    private val argon2 = Argon2Factory.create(
        Argon2Factory.Argon2Types.ARGON2id
    )

    suspend fun create(username: String, password: String): User {
        val byteArray = password.toByteArray()

        val encoded = try {
            argon2.hash(3, 65536, 1, byteArray)
        } catch (_: Exception) {
            throw BadRequestException("Password encoding failed")
        } finally {
            argon2.wipeArray(byteArray)
        }

        val existing = userRepository.findByUsername(username)
        if(existing != null) throw BadRequestException("User with username: $username already exists")

        val user = User(
            username = username,
            displayName = username,
            passwordHash = encoded
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