package eu.dezeekees.melay.server.logic.util

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import eu.dezeekees.melay.server.logic.model.Token
import java.util.Date
import java.util.UUID

object JwtUtil {
    private const val ISSUER = "melay-backend"
    private const val AUDIENCE = "melay-users"
    private const val EXPIRES_IN = 36_000_00 * 24 // 1 day
    const val REALM = "Melay API"

    fun getVerifier(secret: String): JWTVerifier {
        return JWT
            .require(Algorithm.HMAC256(secret))
            .withIssuer(ISSUER)
            .withAudience(AUDIENCE)
            .build()
    }

    fun generateToken(userId: UUID, secret: String): Token {
        val token = JWT.create()
            .withIssuer(ISSUER)
            .withAudience(AUDIENCE)
            .withSubject(userId.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRES_IN))
            .sign(Algorithm.HMAC256(secret))

        return Token(token)
    }
}