package eu.dezeekees.melay.server.api.config

import UserCommunityMembershipDao
import eu.dezeekees.melay.server.data.dao.ChannelDao
import eu.dezeekees.melay.server.data.dao.CommunityDao
import eu.dezeekees.melay.server.data.dao.MessageDao
import eu.dezeekees.melay.server.data.dao.UserDao
import eu.dezeekees.melay.server.logic.broadcast.MessageBroadcaster
import eu.dezeekees.melay.server.logic.repository.ChannelRepository
import eu.dezeekees.melay.server.logic.repository.CommunityRepository
import eu.dezeekees.melay.server.logic.repository.MessageRepository
import eu.dezeekees.melay.server.logic.repository.UserCommunityMembershipRepository
import eu.dezeekees.melay.server.logic.repository.UserRepository
import eu.dezeekees.melay.server.logic.service.AuthService
import eu.dezeekees.melay.server.logic.service.ChannelService
import eu.dezeekees.melay.server.logic.service.CommunityService
import eu.dezeekees.melay.server.logic.service.MessageService
import eu.dezeekees.melay.server.logic.service.UserCommunityMembershipService
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
            single<ChannelRepository> { ChannelDao() }
            single<CommunityRepository> { CommunityDao() }
            single<UserCommunityMembershipRepository> { UserCommunityMembershipDao() }
            single<UserRepository> { UserDao() }
            single<MessageRepository> { MessageDao() }

            single { AuthService(get()) }
            single { ChannelService(get()) }
            single { CommunityService(get()) }
            single { UserCommunityMembershipService(get()) }
            single { UserService(get()) }

            single { MessageBroadcaster() }
            single { MessageService(get(), get()) }
        })
    }
}