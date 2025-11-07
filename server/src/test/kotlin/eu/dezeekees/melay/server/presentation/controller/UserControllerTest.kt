package eu.dezeekees.melay.server.presentation.controller

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.TestApplication
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.api.payload.user.CreateUserRequest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webtestclient.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [TestApplication::class]
)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class UserControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient
    @MockitoBean
    private lateinit var userRepository: UserRepository
    private var passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()
    private val validRequest = CreateUserRequest(
        "testuser",
        "testpassword",
    )

    @Test
    fun `successfully create new user`() {
        val encodedPassword = passwordEncoder.encode("testpassword")?: ""
        val user = User(
            username = validRequest.username,
            displayName = validRequest.username,
            passwordHash = encodedPassword
        )

        whenever(userRepository.save(any())).thenReturn(Mono.just(user))
        whenever(userRepository.findByUsername(any())).thenReturn(Mono.empty())

        webTestClient
            .post()
            .uri(Routes.Api.User.NAME)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(validRequest)
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun `fail when username already exists`() {
        val encodedPassword = passwordEncoder.encode(validRequest.password)?: ""
        val existingUser = User(
            username = validRequest.username,
            displayName = validRequest.username,
            passwordHash = encodedPassword
        )

        whenever(userRepository.findByUsername(any())).thenReturn(Mono.just(existingUser))
        whenever(userRepository.save(any())).thenReturn(Mono.just(existingUser))

        webTestClient.post()
            .uri(Routes.Api.User.NAME)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(validRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errors").exists()
    }

    @Test
    fun `fail when username is too short`() {
        val invalidRequest = validRequest.copy(username = "ab") // less than 3 chars

        webTestClient.post()
            .uri(Routes.Api.User.NAME)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errors").exists()
    }

    @Test
    fun `fail when username contains invalid characters`() {
        val invalidRequest = validRequest.copy(username = "Invalid*Name")

        webTestClient.post()
            .uri(Routes.Api.User.NAME)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errors").exists()
    }


    @Test
    fun `fail when password is too short`() {
        val invalidRequest = validRequest.copy(password = "123") // less than 6 chars

        webTestClient.post()
            .uri(Routes.Api.User.NAME)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidRequest)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody()
            .jsonPath("$.errors").exists()
    }

}