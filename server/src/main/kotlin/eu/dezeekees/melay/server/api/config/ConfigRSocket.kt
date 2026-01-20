package eu.dezeekees.melay.server.api.config


import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.WebSockets
import io.rsocket.kotlin.ktor.server.RSocketSupport

fun Application.configRSocket() {
    install(WebSockets)
    install(RSocketSupport)
}