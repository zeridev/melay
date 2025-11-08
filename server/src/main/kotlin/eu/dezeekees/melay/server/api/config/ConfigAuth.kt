package eu.dezeekees.melay.server.api.config

import eu.dezeekees.melay.server.logic.util.JwtUtil
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond

fun Application.configAuth() {

    val secret = environment.config.property("jwt.secret").getString()

    install(Authentication) {
        jwt("auth-jwt") {
            realm = JwtUtil.REALM
            verifier(JwtUtil.getVerifier(secret))
            validate { credential ->
                val id = credential.payload.getClaim("id").asString()
                if (id != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Invalid or expired access token")
            }
        }
    }
}