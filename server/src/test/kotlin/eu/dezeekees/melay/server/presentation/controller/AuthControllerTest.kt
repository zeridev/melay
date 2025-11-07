package eu.dezeekees.melay.server.presentation.controller

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.TestApplication
import eu.dezeekees.melay.server.data.mapper.UserMapper
import eu.dezeekees.melay.server.data.entity.UserEntity
import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.api.payload.auth.LoginRequest
import eu.dezeekees.melay.server.api.payload.auth.TokenResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webtestclient.AutoConfigureWebTestClient
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.util.*

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [TestApplication::class]
)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class AuthControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient
    @MockitoBean
    lateinit var userRepository: UserRepository
    var passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    @BeforeEach
    fun setUp() {
        val password = passwordEncoder.encode("testpassword") ?: return

        val user = UserMapper.toUser(UserEntity(
            id = UUID.randomUUID(),
            username = "testuser",
            displayName = "testuser",
            passwordHash = password,
        ))

        whenever(userRepository.findByUsername("testuser")).thenReturn(Mono.just(user))
        whenever(userRepository.findByUsername("nonexistentuser")).thenReturn(Mono.empty())
    }

    @Test
    fun `successful login with username and password`() {
        val loginRequest = LoginRequest(
            username = "testuser",
            password = "testpassword",
        )

        val tokenResponse = webTestClient.post()
            .uri(Routes.Api.Auth.LOGIN)
            .bodyValue(loginRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody(TokenResponse::class.java)
            .returnResult()
            .responseBody!!

        assert(tokenResponse.token.isNotEmpty())
    }

    @Test
    fun `login fails when user does not exist`() {
        val loginRequest = LoginRequest(
            username = "nonexistentuser",
            password = "anyPassword"
        )

        webTestClient.post()
            .uri(Routes.Api.Auth.LOGIN)
            .bodyValue(loginRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errors").exists()
    }

    @Test
    fun `login fails when password is incorrect`() {
        val loginRequest = LoginRequest(
            username = "testuser",
            password = "wrongpassword"
        )

        webTestClient.post()
            .uri(Routes.Api.Auth.LOGIN)
            .bodyValue(loginRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errors").exists()
    }

    @Test
    fun `login fails when request body is invalid`() {
        val invalidRequest = mapOf(
            "username" to "", // empty username should trigger validation
            "password" to ""  // empty password
        )

        webTestClient.post()
            .uri(Routes.Api.Auth.LOGIN)
            .bodyValue(invalidRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errors").exists() // Will contain validation errors
    }

}