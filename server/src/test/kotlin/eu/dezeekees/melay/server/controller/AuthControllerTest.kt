package eu.dezeekees.melay.server.controller

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.dto.request.LoginRequest
import eu.dezeekees.melay.server.dto.response.TokenResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [AuthController::class])
class AuthControllerTest(
    @Autowired val webTestClient: WebTestClient
) {
//    @MockitoBean
//    lateinit var userRepository: UserRepository

//    @MockitoBean
//    lateinit var passwordEncoder: PasswordEncoder

//    @BeforeEach
//    fun setUp() {
//        val password = passwordEncoder.encode("testpassword") ?: return
//
//        val user = User(
//            id = "1",
//            username = "testuser",
//            displayName = "testuser",
//            passwordHash = password,
//        )
//
//        whenever(userRepository.findByUsername("testuser")).thenReturn(Mono.just(user))
//        whenever(userRepository.findByUsername("unknown")).thenReturn(Mono.empty())
//    }

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