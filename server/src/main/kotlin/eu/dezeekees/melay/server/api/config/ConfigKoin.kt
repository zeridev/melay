package eu.dezeekees.melay.server.api.config

import eu.dezeekees.melay.server.data.dao.UserDao
import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.logic.service.AuthService
import eu.dezeekees.melay.server.logic.service.UserService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configKoin() {
    install(Koin) {
        slf4jLogger()
        modules(module {
            single<UserRepository> { UserDao() }
            single { UserService(get()) }
            single { AuthService(get()) }
        })
    }
}