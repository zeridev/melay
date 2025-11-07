package eu.dezeekees.melay.server.api

import eu.dezeekees.melay.server.api.config.DatabaseConfig
import eu.dezeekees.melay.server.api.config.configAuth
import eu.dezeekees.melay.server.api.config.configContentNegotiation
import eu.dezeekees.melay.server.api.config.configKoin
import eu.dezeekees.melay.server.api.config.configRoutes
import eu.dezeekees.melay.server.api.config.configStatusPage
import eu.dezeekees.melay.server.api.config.configValidation
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseConfig.init(environment.config)

    configKoin()
    configContentNegotiation()
    configAuth()
    configStatusPage()
    configValidation()
    configRoutes()
}
