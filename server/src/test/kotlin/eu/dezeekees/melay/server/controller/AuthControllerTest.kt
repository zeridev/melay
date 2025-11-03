package eu.dezeekees.melay.server.controller

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.TestApplication
import eu.dezeekees.melay.server.data.entity.UserEntity
import eu.dezeekees.melay.server.data.entity.toUser
import eu.dezeekees.melay.server.logic.dto.request.LoginRequest
import eu.dezeekees.melay.server.logic.dto.response.TokenResponse
import eu.dezeekees.melay.server.logic.repository.UserRepository
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
class AuthControllerTest(
    @Autowired val webTestClient: WebTestClient
) {
    @MockitoBean
    lateinit var userRepository: UserRepository
    var passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    @BeforeEach
    fun setUp() {
        val password = passwordEncoder.encode("testpassword") ?: return

        val user = UserEntity(
            id = UUID.randomUUID(),
            username = "testuser",
            displayName = "testuser",
            passwordHash = password,
        ).toUser()

        whenever(userRepository.findByUsername("testuser")).thenReturn(Mono.just(user))
        whenever(userRepository.findByUsername("unknown")).thenReturn(Mono.empty())
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

}