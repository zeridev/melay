package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.TestBase
import eu.dezeekees.melay.server.api.mapper.UserMapper
import eu.dezeekees.melay.server.api.payload.auth.RegisterUserRequest
import eu.dezeekees.melay.server.api.payload.user.UserResponse
import eu.dezeekees.melay.server.logic.model.User
import eu.dezeekees.melay.server.logic.util.PasswordUtil
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.datetime.Clock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.*

class UserRoutesTest: TestBase(), KoinTest {
    private val validRequest = RegisterUserRequest(
        "testuser",
        "testpassword",
    )

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