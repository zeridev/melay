package eu.dezeekees.melay.app.di

import eu.dezeekees.melay.app.presentation.viewmodel.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    viewModelOf(::LoginViewModel)
}