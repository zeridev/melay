package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.TestBase
import eu.dezeekees.melay.server.api.payload.UuidRequest
import eu.dezeekees.melay.server.api.payload.community.CreateCommunityRequest
import eu.dezeekees.melay.server.api.payload.community.UpdateCommunityRequest
import eu.dezeekees.melay.server.api.payload.community.UserCommunityMembershipRequest
import eu.dezeekees.melay.server.logic.model.Community
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CommunityRoutesTest : TestBase(), KoinTest {
    private val createRequest = CreateCommunityRequest(
        name = "test_community",
        description = "A test community"
    )

    private val updateRequest = UpdateCommunityRequest(
        id = UUID.randomUUID(),
        name = "updated_community",
        description = "Updated description"
    )

    private val uuidRequest = UuidRequest(
        uuid = UUID.randomUUID()
    )

    private val membershipRequest = UserCommunityMembershipRequest(
        userId = UUID.randomUUID(),
        communityId = UUID.randomUUID()
    )

    private val community = Community(
        id = UUID.randomUUID(),
        name = "test_community",
        description = "A test community",
        iconUrl = "",
        bannerUrl = "",
        createdAt = Clock.System.now(),
        channels = emptyList(),
    )

    @Test
    fun `get all communities returns ok`() = testBlock {
        whenever(mockCommunityRepository.findAll()) doReturn  listOf(community)

        val response = client.get(Routes.Api.Community.NAME)

        assertEquals(HttpStatusCode.OK, response.status)
        val body: String = response.body()
        assertTrue(body.isNotEmpty())
    }

    @Test
    fun `create community returns created community`() = testBlock {
        whenever(mockCommunityRepository.create(any())) doReturn community

        val response = client.post(Routes.Api.Community.NAME) {
            contentType(ContentType.Application.Json)
            setBody(createRequest)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body: String = response.body()
        assertTrue(body.isNotEmpty())
    }

    @Test
    fun `update community returns updated community`() = testBlock {
        whenever(mockCommunityRepository.update(any())) doReturn community

        val response = client.patch(Routes.Api.Community.NAME) {
            contentType(ContentType.Application.Json)
            setBody(updateRequest)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body: String = response.body()
        assertTrue(body.isNotEmpty())
    }

    @Test
    fun `delete community returns ok`() = testBlock {
        whenever(mockCommunityRepository.delete(any<UUID>())).thenAnswer { }
        val route = "${Routes.Api.Community.NAME}/${uuidRequest.uuid}"
        val response = client.delete(route)

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `add membership returns community`() = testBlock {
        // ensure addMembership does not throw and findById returns community
        whenever(mockUserCommunityMembershipRepository.addMembership(any(), any())) doReturn Unit
        whenever(mockCommunityRepository.findById(any())) doReturn community

        val response = client.post(Routes.Api.Community.MEMBERS) {
            contentType(ContentType.Application.Json)
            setBody(membershipRequest)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val body: String = response.body()
        assertTrue(body.isNotEmpty())
    }

    @Test
    fun `remove membership returns ok`() = testBlock {
        whenever(mockUserCommunityMembershipRepository.removeMembership(any(), any())) doReturn Unit

        val response = client.delete("${Routes.Api.Community.MEMBERS}/${membershipRequest.communityId}/${membershipRequest.userId}")

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `get members with invalid id returns bad request`() = testBlock {
        val response = client.get("${Routes.Api.Community.MEMBERS}/not-a-uuid")
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `create fails when name is blank`() = testBlock {
        val bad = createRequest.copy(name = "")
        val response = client.post(Routes.Api.Community.NAME) {
            contentType(ContentType.Application.Json)
            setBody(bad)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `create fails when name contains invalid characters`() = testBlock {
        val bad = createRequest.copy(name = "Invalid Name!")
        val response = client.post(Routes.Api.Community.NAME) {
            contentType(ContentType.Application.Json)
            setBody(bad)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `create fails when name is too short`() = testBlock {
        val bad = createRequest.copy(name = "ab")
        val response = client.post(Routes.Api.Community.NAME) {
            contentType(ContentType.Application.Json)
            setBody(bad)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `update fails when name is too short`() = testBlock {
        val bad = updateRequest.copy(name = "ab")
        val response = client.patch(Routes.Api.Community.NAME) {
            contentType(ContentType.Application.Json)
            setBody(bad)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `update fails when name contains invalid characters`() = testBlock {
        val bad = updateRequest.copy(name = "Bad Name!")
        val response = client.patch(Routes.Api.Community.NAME) {
            contentType(ContentType.Application.Json)
            setBody(bad)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}