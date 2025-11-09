package eu.dezeekees.melay.server.logic.util

import de.mkammerer.argon2.Argon2
import de.mkammerer.argon2.Argon2Factory
import eu.dezeekees.melay.server.logic.exception.BadRequestException

object PasswordUtil {
    // Parameters tuned for security (can be increased as hardware allows)
    private const val ITERATIONS = 3
    private const val MEMORY = 65536  // 64MB
    private const val PARALLELISM = 1

    private val argon2: Argon2 = Argon2Factory.create(
        Argon2Factory.Argon2Types.ARGON2id
    )

    fun hash(password: String): String {
        val byteArray = password.toByteArray()

        return try {
            argon2.hash(ITERATIONS, MEMORY, PARALLELISM, byteArray)
        } catch (_: Exception) {
            throw BadRequestException("Password encoding failed")
        } finally {
            argon2.wipeArray(byteArray)
        }
    }

    fun verify(password: String, passwordHash: String): Boolean {
        val byteArray = password.toByteArray()
        return argon2.verify(passwordHash, byteArray)
    }
}