package eu.dezeekees.melay.server

import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.logic.service.AuthService
import eu.dezeekees.melay.server.logic.service.UserService
import eu.dezeekees.melay.server.logic.util.JwtUtil
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.bearerAuth
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.koin.ktor.plugin.Koin
import org.mockito.kotlin.mock
import java.util.UUID

abstract class TestBase {
    val mockUserRepository = mock<UserRepository>()

    fun testBlock(
        block: suspend ApplicationTestBuilder.() -> Unit
    ) {
        testApplication {
            environment {
                config = ApplicationConfig("application-test.conf")
            }
            application {
                install(Koin) {
                    modules(org.koin.dsl.module {
                        single<UserRepository> { mockUserRepository }
                        single { UserService(get()) }
                        single { AuthService(get()) }
                    })
                }
                module()
            }
            client = createClient {
                install(ContentNegotiation) { json() }
                defaultRequest {
                    bearerAuth(JwtUtil.generateToken(UUID.randomUUID(), "super-secret-secret").token)
                }
            }

            block()
        }
    }
}