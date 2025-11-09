package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.TestBase
import eu.dezeekees.melay.server.api.mapper.UserMapper
import eu.dezeekees.melay.server.api.payload.user.CreateUserRequest
import eu.dezeekees.melay.server.api.payload.user.UserResponse
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.logic.service.UserService
import eu.dezeekees.melay.server.logic.util.JwtUtil
import eu.dezeekees.melay.server.logic.util.PasswordUtil
import eu.dezeekees.melay.server.module
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlinx.datetime.Clock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.ktor.plugin.Koin
import org.koin.test.KoinTest
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class UserRoutesTest: TestBase(), KoinTest {
    private val validRequest = CreateUserRequest(
        "testuser",
        "testpassword",
    )

    @Test
    fun `successfully create new user`() = testBlock {
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

        val response: UserResponse = client.post(Routes.Api.User.NAME) {
            contentType(ContentType.Application.Json)
            setBody(validRequest)
        }.body()

        assertEquals(UserMapper.toResponse(user), response)
    }

    @Test
    fun `fail when username already exists`() = testBlock {
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

        val response = client.post(Routes.Api.User.NAME) {
            contentType(ContentType.Application.Json)
            setBody(validRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `fail when username is too short`() = testBlock {
        val invalidRequest = validRequest.copy(username = "ab")

        val response = client.post(Routes.Api.User.NAME) {
            contentType(ContentType.Application.Json)
            setBody(invalidRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `fail when username contains invalid characters`() = testBlock {
        val invalidRequest = validRequest.copy(username = "Invalid*Name")

        val response = client.post(Routes.Api.User.NAME) {
            contentType(ContentType.Application.Json)
            setBody(invalidRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `fail when username is blank`() = testBlock {
        val invalidRequest = validRequest.copy(username = "  ")

        val response = client.post(Routes.Api.User.NAME) {
            contentType(ContentType.Application.Json)
            setBody(invalidRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `fail when password is blank`() = testBlock {
        val invalidRequest = validRequest.copy(password = "  ")

        val response = client.post(Routes.Api.User.NAME) {
            contentType(ContentType.Application.Json)
            setBody(invalidRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `fail when password is too short`() = testBlock {
        val invalidRequest = validRequest.copy(password = "test")

        val response = client.post(Routes.Api.User.NAME) {
            contentType(ContentType.Application.Json)
            setBody(invalidRequest)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `get user by id`() = testBlock {
        val passwordHash = PasswordUtil.hash(validRequest.password)
        val uuid = UUID.randomUUID()
        val user = User(
            id = uuid,
            username = validRequest.username,
            displayName = validRequest.username,
            passwordHash = passwordHash,
            createdAt = Clock.System.now(),
        )

        whenever(mockUserRepository.findById(any())).thenReturn(user)

        val response: UserResponse = client.get(Routes.Api.User.NAME + "/${user.id}")
            .body()

        assertEquals(UserMapper.toResponse(user), response)
    }
}