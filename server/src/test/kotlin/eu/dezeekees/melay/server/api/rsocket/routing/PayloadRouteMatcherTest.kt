package eu.dezeekees.melay.server.api.rsocket.routing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PayloadRouteMatcherTest {

    // -----------------------------
    // parseRoute()
    // -----------------------------

    @Test
    fun `parse static route successfully`() {
        val staticRoute = "stream.messages"
        val matcher = PayloadRouteMatcher(null)

        val expected = ParsedRoute(
            name = "stream.messages",
            params = emptyMap()
        )

        val actual = matcher.parseRoute(staticRoute)

        assertEquals(expected, actual)
    }

    @Test
    fun `parse dynamic route successfully`() {
        val dynamicRoute = "stream.{channel_id:1}.messages"
        val matcher = PayloadRouteMatcher(null)

        val expected = ParsedRoute(
            name = "stream.channel_id.messages",
            params = mapOf("channel_id" to "1")
        )

        val actual = matcher.parseRoute(dynamicRoute)

        assertEquals(expected, actual)
    }

    @Test
    fun `parse dynamic route with multiple params`() {
        val route = "a.{x:10}.b.{y:20}.c"
        val matcher = PayloadRouteMatcher(null)

        val expected = ParsedRoute(
            name = "a.x.b.y.c",
            params = mapOf("x" to "10", "y" to "20")
        )

        val actual = matcher.parseRoute(route)

        assertEquals(expected, actual)
    }

    @Test
    fun `parse dynamic route with empty param value`() {
        val route = "stream.{id:}.messages"
        val matcher = PayloadRouteMatcher(null)

        val expected = ParsedRoute(
            name = "stream.id.messages",
            params = emptyMap() // param exists but has no value → should NOT be added
        )

        val actual = matcher.parseRoute(route)

        assertEquals(expected, actual)
    }


    // -----------------------------
    // match()
    // -----------------------------
    @Test
    fun `match returns parsed route when names match`() {
        val incoming = "stream.{channel_id:42}.messages"
        val matcher = PayloadRouteMatcher(incoming)

        val result = matcher.match("stream.channel_id.messages")

        assertNotNull(result)
        assertEquals("stream.channel_id.messages", result!!.name)
        assertEquals(mapOf("channel_id" to "42"), result.params)
    }

    @Test
    fun `match returns null when names do not match`() {
        val incoming = "stream.{channel:42}.messages"
        val matcher = PayloadRouteMatcher(incoming)

        val result = matcher.match("other.route")

        assertNull(result)
    }

    @Test
    fun `match returns null when incoming routeString is null`() {
        val matcher = PayloadRouteMatcher(null)
        val result = matcher.match("stream.messages")

        assertNull(result)
    }

    @Test
    fun `match works with multiple dynamic params`() {
        val incoming = "a.{x:10}.b.{y:20}.c"
        val matcher = PayloadRouteMatcher(incoming)

        val result = matcher.match("a.{x:1}.b.{y:2}.c")

        assertNotNull(result)
        assertEquals("a.x.b.y.c", result!!.name)
        assertEquals(mapOf("x" to "10", "y" to "20"), result.params)
    }

    @Test
    fun `match ignores param values during comparison`() {
        val matcher = PayloadRouteMatcher("stream.{id:123}.messages")

        // same route shape → match OK
        val result = matcher.match("stream.{id:999}.messages")

        assertNotNull(result)
        assertEquals("stream.id.messages", result!!.name)
        assertEquals(mapOf("id" to "123"), result.params)
    }

    @Test
    fun `match fails when route shape differs`() {
        val matcher = PayloadRouteMatcher("stream.{id:1}.messages")

        // different name shape (`stream.x.messages` vs `stream.id.messages`)
        val result = matcher.match("stream.x.messages")

        assertNull(result)
    }
}
