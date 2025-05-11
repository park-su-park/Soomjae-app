package com.parksupark.soomjae.features.auth.presentation.di

import com.parksupark.soomjae.features.auth.presentation.login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val loginModule = module {
    viewModelOf(::LoginViewModel)
}

val featuresAuthPresentationModule = module {
    includes(loginModule)
}
