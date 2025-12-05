package eu.dezeekees.melay.server.api.rsocket.routing

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class SerializationTest {

    @Test
    fun `parse static route successfully`() {
        val staticRoute = "stream.messages"

        val expected = ParsedRoute(
            name = staticRoute,
            params = emptyMap()
        )

        val actual = parseRoute(staticRoute)

        assertEquals(expected, actual)
    }

    @Test
    fun `parse dynamic route successfully`() {
        val dynamicRoute = "stream.{channel_id:1}.messages"

        val expected = ParsedRoute(
            name = "stream.channel_id.messages",
            params = mapOf(
                "channel_id" to "1"
            )
        )

        val actual = parseRoute(dynamicRoute)

        assertEquals(expected, actual)
    }

}