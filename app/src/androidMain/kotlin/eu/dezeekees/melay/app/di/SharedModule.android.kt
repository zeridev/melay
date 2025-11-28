package eu.dezeekees.melay.app.di

import eu.dezeekees.melay.app.data.TokenStoreDaoAndroid
import eu.dezeekees.melay.app.logic.repository.TokenStoreRepository
import org.koin.dsl.module

actual val platformModule = module {
    single<TokenStoreRepository> { TokenStoreDaoAndroid(get()) }
}