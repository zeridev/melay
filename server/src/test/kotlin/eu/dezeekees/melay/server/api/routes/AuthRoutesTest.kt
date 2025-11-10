package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.TestBase
import eu.dezeekees.melay.server.api.mapper.UserMapper
import eu.dezeekees.melay.server.api.payload.auth.LoginRequest
import eu.dezeekees.melay.server.api.payload.auth.RegisterUserRequest
import eu.dezeekees.melay.server.api.payload.auth.TokenResponse
import eu.dezeekees.melay.server.api.payload.user.UserResponse
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.util.PasswordUtil
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.datetime.Clock
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import java.util.UUID
import kotlin.test.assertEquals

class AuthRoutesTest: TestBase(), KoinTest {
    val loginRequest = LoginRequest(
        username = "testuser",
        password = "testpassword",
    )
    val passwordHash = PasswordUtil.hash(loginRequest.password)
    val user = User(
        id = UUID.randomUUID(),
        username = loginRequest.username,
        displayName = loginRequest.username,
        passwordHash = passwordHash,
        createdAt = Clock.System.now(),
    )

    @Test
    fun `successful login with username and password`() = testBlock {
        whenever(mockUserRepository.findByUsername(loginRequest.username)) doReturn user

        val response: TokenResponse = client.post(Routes.Api.Auth.LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()

        assert(response.token.isNotEmpty())
    }

    @Test
    fun `login fails when user does not exist`() = testBlock {
        whenever(mockUserRepository.findByUsername(any())) doReturn null

        val response = client.post(Routes.Api.Auth.LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `login fails when password is incorrect`() = testBlock {
        whenever(mockUserRepository.findByUsername(loginRequest.username)) doReturn user

        val response = client.post(Routes.Api.Auth.LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest.copy(
                password = "wrong_password"
            ))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `login fails username is empty`() = testBlock {
        val response = client.post(Routes.Api.Auth.LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest.copy(
                password = "",
            ))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `login fails password is empty`() = testBlock {
        val response = client.post(Routes.Api.Auth.LOGIN) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest.copy(
                password = "",
            ))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    private val validRequest = RegisterUserRequest(
        "testuser",
        "testpassword",
    )

    @Test
    fun `successfully register new user`() = testBlock {
        val passwordHash = PasswordUtil.hash(validRequest.password)
        val user = User(
            id = UUID.randomUUID(),
            username = validRequest.username,
            displayName = validRequest.username,
            passwordHash = passwordHash,
            createdAt = Clock.System.now(),
        )

        whenever(mockUserRepository.save(any())).thenReturn(user)
        whenever(mockUserRepository.findByUsername(any())).thenReturn(null)

        val response: UserResponse = client.post(Routes.Api.Auth.REGISTER) {
            contentType(ContentType.Application.Json)
            setBody(validRequest)
        }.body()

        assertEquals(UserMapper.toResponse(user), response)
    }

    @Test
    fun `fail register when username already exists`() = testBlock {
        val passwordHash = PasswordUtil.hash(validRequest.password)
        val existingUser = User(
            id = UUID.randomUUID(),
            username = validRequest.username,
            displayName = validRequest.username,
            passwordHash = passwordHash,
            createdAt = Clock.System.now(),
        )

        whenever(mockUserRepository.save(any())).thenReturn(existingUser)
        whenever(mockUserRepository.findByUsername(any())).thenReturn(existingUser)

        val response = client.post(Routes.Api.Auth.REGISTER) {
            contentType(ContentType.Application.Json)
            setBody(validRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `fail register when username is too short`() = testBlock {
        val invalidRequest = validRequest.copy(username = "ab")

        val response = client.post(Routes.Api.Auth.REGISTER) {
            contentType(ContentType.Application.Json)
            setBody(invalidRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `fail register when username contains invalid characters`() = testBlock {
        val invalidRequest = validRequest.copy(username = "Invalid*Name")

        val response = client.post(Routes.Api.Auth.REGISTER) {
            contentType(ContentType.Application.Json)
            setBody(invalidRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `fail register when username is blank`() = testBlock {
        val invalidRequest = validRequest.copy(username = "  ")

        val response = client.post(Routes.Api.Auth.REGISTER) {
            contentType(ContentType.Application.Json)
            setBody(invalidRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `fail register when password is blank`() = testBlock {
        val invalidRequest = validRequest.copy(password = "  ")

        val response = client.post(Routes.Api.Auth.REGISTER) {
            contentType(ContentType.Application.Json)
            setBody(invalidRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `fail register when password is too short`() = testBlock {
        val invalidRequest = validRequest.copy(password = "test")

        val response = client.post(Routes.Api.Auth.REGISTER) {
            contentType(ContentType.Application.Json)
            setBody(invalidRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}