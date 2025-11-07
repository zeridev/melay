package eu.dezeekees.melay.server.logic

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import eu.dezeekees.melay.server.logic.model.Token
import java.util.Date
import java.util.UUID

object JwtUtil {
    private val issuer = "melay-backend"
    private val audience = "melay-users"
    val realm = "Melay API"

    private val validityInMs = 36_000_00 * 24 // 1 day

    fun getVerifier(secret: String): JWTVerifier {
        return JWT
            .require(Algorithm.HMAC256(secret))
            .withIssuer(issuer)
            .withAudience(audience)
            .build()
    }

    fun generateToken(userId: UUID, secret: String): Token {
        val token = JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("id", userId.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + validityInMs))
            .sign(Algorithm.HMAC256(secret))

        return Token(token)
    }
}