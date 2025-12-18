package eu.dezeekees.melay.server.api.payload.auth
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)