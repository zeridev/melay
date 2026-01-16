package eu.dezeekees.melay.app.di

import eu.dezeekees.melay.app.logic.`interface`.IAuthStateProvider
import eu.dezeekees.melay.app.logic.`interface`.IRSocketClient
import eu.dezeekees.melay.app.logic.repository.AuthRepository
import eu.dezeekees.melay.app.logic.repository.ChannelRepository
import eu.dezeekees.melay.app.logic.repository.CommunityRepository
import eu.dezeekees.melay.app.logic.repository.UserRepository
import eu.dezeekees.melay.app.logic.service.AuthService
import eu.dezeekees.melay.app.logic.service.AuthStateProvider
import eu.dezeekees.melay.app.logic.service.ChannelService
import eu.dezeekees.melay.app.logic.service.CommunityService
import eu.dezeekees.melay.app.logic.service.TokenService
import eu.dezeekees.melay.app.logic.service.UserService
import eu.dezeekees.melay.app.network.HttpClientProvider
import eu.dezeekees.melay.app.network.http.client.AuthClient
import eu.dezeekees.melay.app.network.http.client.ChannelClient
import eu.dezeekees.melay.app.network.http.client.CommunityClient
import eu.dezeekees.melay.app.network.http.client.UserClient
import eu.dezeekees.melay.app.network.rsocket.RSocketClient
import eu.dezeekees.melay.app.presentation.viewmodel.AppViewModel
import eu.dezeekees.melay.app.presentation.viewmodel.LoginViewModel
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel
import eu.dezeekees.melay.app.presentation.viewmodel.main.CreateChannelPopupViewModel
import eu.dezeekees.melay.app.presentation.viewmodel.main.CreateCommunityPopupViewModel
import eu.dezeekees.melay.app.presentation.viewmodel.main.DiscoverCommunityPopupViewModel
import eu.dezeekees.melay.app.presentation.viewmodel.main.UpdateCommunityPopupViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::HttpClientProvider)
    single<IRSocketClient> { RSocketClient(get()) }
    single<IAuthStateProvider> { AuthStateProvider(get()) }

    singleOf(::TokenService)

    single<AuthRepository> { AuthClient(get()) }
    singleOf(::AuthService)

    single<UserRepository> { UserClient(get()) }
    singleOf(::UserService)

    single<ChannelRepository> { ChannelClient(get()) }
    singleOf(::ChannelService)

    single<CommunityRepository> { CommunityClient(get()) }
    singleOf(::CommunityService)

    viewModelOf(::LoginViewModel)
    viewModelOf(::MainScreenViewmodel)
    viewModelOf(::CreateChannelPopupViewModel)
    viewModelOf(::CreateCommunityPopupViewModel)
    viewModelOf(::UpdateCommunityPopupViewModel)
    viewModelOf(::DiscoverCommunityPopupViewModel)
    viewModelOf(::AppViewModel)
}

expect val platformModule: Module