package eu.dezeekees.melay.server.logic.service

import de.mkammerer.argon2.Argon2Factory
import eu.dezeekees.melay.server.logic.JwtUtil
import eu.dezeekees.melay.server.logic.exception.BadRequestException
import eu.dezeekees.melay.server.logic.model.Token
import eu.dezeekees.melay.server.logic.repository.UserRepository

class AuthService(
    private val userRepository: UserRepository
) {
    private val argon2 = Argon2Factory.create(
        Argon2Factory.Argon2Types.ARGON2id
    )

    suspend fun login(username: String, password: String, secret: String): Token {
        val user = userRepository.findByUsername(username) ?: throw BadRequestException("User does not exist or password is incorrect")
        val byteArray = password.toByteArray()

        if(user.id == null) throw BadRequestException("User does not exist or password is incorrect")

        if(!argon2.verify(user.passwordHash, byteArray)) {
            throw BadRequestException("User does not exist or password is incorrect")
        }

        return JwtUtil.generateToken(user.id, secret)
    }
}