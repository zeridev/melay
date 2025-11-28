package eu.dezeekees.melay.app.di

import eu.dezeekees.melay.app.data.TokenStoreDaoJvm
import eu.dezeekees.melay.app.logic.repository.TokenStoreRepository
import org.koin.dsl.module

actual val platformModule = module {
    single<TokenStoreRepository> { TokenStoreDaoJvm() }
}