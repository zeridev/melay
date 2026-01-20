package eu.dezeekees.melay.server.api.routes

import eu.dezeekees.melay.common.Routes
import eu.dezeekees.melay.server.TestBase
import eu.dezeekees.melay.server.api.payload.channel.CreateChannelRequest
import eu.dezeekees.melay.server.api.payload.channel.UpdateChannelRequest
import eu.dezeekees.melay.server.logic.model.Channel
import eu.dezeekees.melay.common.payload.ChannelType
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.datetime.Clock
import org.junit.jupiter.api.Test
import org.koin.test.KoinTest
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ChannelRoutesTest : TestBase(), KoinTest {
	private val createRequest = CreateChannelRequest(
		name = "test-channel",
		position = 1,
		type = ChannelType.TEXT,
		communityId = UUID.randomUUID()
	)

	private val updateRequest = UpdateChannelRequest(
		name = "updated-channel",
		position = 2,
		type = ChannelType.TEXT
	)

	private val channel = Channel(
		id = UUID.randomUUID(),
		name = "test-channel",
		type = ChannelType.TEXT,
		position = 1,
		communityId = UUID.randomUUID(),
		createdAt = Clock.System.now(),
	)

	@Test
	fun `create channel returns created channel`() = testBlock {
		whenever(mockChannelRepository.create(any())) doReturn channel

		val response = client.post(Routes.Api.Channel.NAME) {
			contentType(ContentType.Application.Json)
			setBody(createRequest)
		}

		assertEquals(HttpStatusCode.OK, response.status)
		val body: String = response.body()
		assertTrue(body.isNotEmpty())
	}

	@Test
	fun `update channel returns updated channel`() = testBlock {
		whenever(mockChannelRepository.update(any())) doReturn channel

		val response = client.patch(Routes.Api.Channel.NAME) {
			contentType(ContentType.Application.Json)
			setBody(updateRequest)
		}

		assertEquals(HttpStatusCode.OK, response.status)
		val body: String = response.body()
		assertTrue(body.isNotEmpty())
	}

	@Test
	fun `delete channel returns ok`() = testBlock {
		whenever(mockChannelRepository.delete(any<UUID>())).thenAnswer { }

		val route = "${Routes.Api.Channel.NAME}/${channel.id}"
		val response = client.delete(route)

		assertEquals(HttpStatusCode.OK, response.status)
	}

	@Test
	fun `delete with invalid id returns bad request`() = testBlock {
		val response = client.delete("${Routes.Api.Channel.NAME}/not-a-uuid")
		assertEquals(HttpStatusCode.BadRequest, response.status)
	}

	@Test
	fun `create fails when name is blank`() = testBlock {
		val bad = createRequest.copy(name = "")
		val response = client.post(Routes.Api.Channel.NAME) {
			contentType(ContentType.Application.Json)
			setBody(bad)
		}

		assertEquals(HttpStatusCode.BadRequest, response.status)
	}

	@Test
	fun `create fails when name contains invalid characters`() = testBlock {
		val bad = createRequest.copy(name = "Invalid Name!")
		val response = client.post(Routes.Api.Channel.NAME) {
			contentType(ContentType.Application.Json)
			setBody(bad)
		}

		assertEquals(HttpStatusCode.BadRequest, response.status)
	}

	@Test
	fun `create fails when name is too short`() = testBlock {
		val bad = createRequest.copy(name = "ab")
		val response = client.post(Routes.Api.Channel.NAME) {
			contentType(ContentType.Application.Json)
			setBody(bad)
		}

		assertEquals(HttpStatusCode.BadRequest, response.status)
	}

}