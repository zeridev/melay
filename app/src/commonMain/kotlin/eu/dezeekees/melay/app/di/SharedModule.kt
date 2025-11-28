package eu.dezeekees.melay.app.di

import eu.dezeekees.melay.app.logic.AuthService
import eu.dezeekees.melay.app.logic.repository.AuthRepository
import eu.dezeekees.melay.app.network.http.client.AuthClient
import eu.dezeekees.melay.app.network.http.createHttpClient
import eu.dezeekees.melay.app.presentation.navigation.NavigationHandler
import eu.dezeekees.melay.app.presentation.navigation.NavigationManager
import eu.dezeekees.melay.app.presentation.viewmodel.LoginViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    single<HttpClient> { createHttpClient() }
    singleOf(::NavigationManager)

    single<AuthRepository> { AuthClient(get()) }
    singleOf(::AuthService)

    viewModelOf(::LoginViewModel)
}

expect val platformModule: Module