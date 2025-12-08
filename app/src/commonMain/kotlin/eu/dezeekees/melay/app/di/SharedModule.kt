package eu.dezeekees.melay.app.di

import eu.dezeekees.melay.app.logic.AuthService
import eu.dezeekees.melay.app.logic.`interface`.IRSocketClient
import eu.dezeekees.melay.app.logic.repository.AuthRepository
import eu.dezeekees.melay.app.network.http.client.AuthClient
import eu.dezeekees.melay.app.network.createHttpClient
import eu.dezeekees.melay.app.network.rsocket.RSocketClient
import eu.dezeekees.melay.app.presentation.navigation.NavigationManager
import eu.dezeekees.melay.app.presentation.viewmodel.LoginViewModel
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<HttpClient> { createHttpClient() }
    single<IRSocketClient> { RSocketClient(get()) }
    singleOf(::NavigationManager)

    single<AuthRepository> { AuthClient(get()) }
    singleOf(::AuthService)

    viewModelOf(::LoginViewModel)
    viewModelOf(::MainScreenViewmodel)
}

expect val platformModule: Module