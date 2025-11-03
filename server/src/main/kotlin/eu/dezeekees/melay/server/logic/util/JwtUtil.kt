package eu.dezeekees.melay.server.logic.util

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import java.util.Date
import java.util.UUID

object JwtUtil {
    private val secret = "super-secret-key-which-should-be-long".toByteArray()
    private val signer = MACSigner(secret)
    private val verifier = MACVerifier(secret)

    fun generateToken(uuid: UUID): String {
        val claims = JWTClaimsSet.Builder()
            .subject(uuid.toString())
            .expirationTime(Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 min
            .issuer("melay")
            .build()
        val jwt = SignedJWT(JWSHeader(JWSAlgorithm.HS256), claims)
        jwt.sign(signer)
        return jwt.serialize()
    }

    fun validateToken(token: String): String? {
        val jwt = SignedJWT.parse(token)
        return if (jwt.verify(verifier) && Date().before(jwt.jwtClaimsSet.expirationTime)) {
            jwt.jwtClaimsSet.subject
        } else null
    }
}