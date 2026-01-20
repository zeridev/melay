package eu.dezeekees.melay.server

import eu.dezeekees.melay.server.logic.repository.ChannelRepository
import eu.dezeekees.melay.server.logic.repository.CommunityRepository
import eu.dezeekees.melay.server.logic.repository.UserCommunityMembershipRepository
import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.logic.service.AuthService
import eu.dezeekees.melay.server.logic.service.ChannelService
import eu.dezeekees.melay.server.logic.service.CommunityService
import eu.dezeekees.melay.server.logic.service.UserCommunityMembershipService
import eu.dezeekees.melay.server.logic.service.UserService
import eu.dezeekees.melay.server.logic.util.JwtUtil
import eu.dezeekees.melay.common.kotlinx.UUIDSerializer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.bearerAuth
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.koin.ktor.plugin.Koin
import org.mockito.kotlin.mock
import java.util.UUID

abstract class TestBase {
    val mockUserRepository = mock<UserRepository>()
    val mockCommunityRepository = mock<CommunityRepository>()
    val mockChannelRepository = mock<ChannelRepository>()
    val mockUserCommunityMembershipRepository = mock<UserCommunityMembershipRepository>()

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
                        single<CommunityRepository> { mockCommunityRepository }
                        single<ChannelRepository> { mockChannelRepository }
                        single<UserCommunityMembershipRepository> { mockUserCommunityMembershipRepository }

                        single { CommunityService(get()) }
                        single { ChannelService(get()) }
                        single { UserCommunityMembershipService(get()) }
                        single { UserService(get()) }
                        single { AuthService(get()) }
                    })
                }
                module()
            }
            client = createClient {
                install(ContentNegotiation) { json(Json {
                    serializersModule = SerializersModule {
                        contextual(UUID::class, UUIDSerializer)
                    }
                }) }
                defaultRequest {
                    bearerAuth(JwtUtil.generateToken(UUID.randomUUID(), "super-secret-secret").token)
                }
            }

            block()
        }
    }
}