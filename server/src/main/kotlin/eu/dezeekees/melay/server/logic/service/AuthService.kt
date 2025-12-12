package eu.dezeekees.melay.server.logic.service

import eu.dezeekees.melay.server.logic.util.JwtUtil
import eu.dezeekees.melay.server.logic.exception.BadRequestException
import eu.dezeekees.melay.server.logic.model.Token
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.logic.util.PasswordUtil

class AuthService(
    private val userRepository: UserRepository
) {
    suspend fun login(username: String, password: String, secret: String): Token {
        val user = userRepository.findByUsername(username) ?: throw BadRequestException("User does not exist or password is incorrect")
        if(user.id == null) throw BadRequestException("User does not exist or password is incorrect")
        if(!PasswordUtil.verify(password, user.passwordHash)) throw BadRequestException("User does not exist or password is incorrect")

        return JwtUtil.generateToken(user.id, secret)
    }

    suspend fun register(username: String, password: String): User {
        val existing = userRepository.findByUsername(username)
        if(existing != null) throw BadRequestException("User with username: $username already exists")

        val user = User(
            username = username,
            displayName = username,
            passwordHash = PasswordUtil.hash(password)
        )

        return userRepository.create(user)
    }
}