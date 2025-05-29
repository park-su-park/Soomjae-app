package com.parksupark.soomjae.features.auth.presentation.di

import com.parksupark.soomjae.features.auth.presentation.emaillogin.EmailLoginViewModel
import com.parksupark.soomjae.features.auth.presentation.login.LoginViewModel
import com.parksupark.soomjae.features.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val emailLoginModule = module {
    viewModelOf(::EmailLoginViewModel)
}

private val loginModule = module {
    viewModelOf(::LoginViewModel)
}

private val registerModule = module {
    viewModelOf(::RegisterViewModel)
}

val featuresAuthPresentationModule = module {
    includes(emailLoginModule, loginModule, registerModule)
}
