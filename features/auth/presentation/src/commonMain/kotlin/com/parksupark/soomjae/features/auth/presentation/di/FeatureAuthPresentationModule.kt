package com.parksupark.soomjae.features.auth.presentation.di

import com.parksupark.soomjae.features.auth.libs.google.di.googleAuthModule
import com.parksupark.soomjae.features.auth.presentation.email_verification.EmailVerificationViewModel
import com.parksupark.soomjae.features.auth.presentation.emaillogin.EmailLoginViewModel
import com.parksupark.soomjae.features.auth.presentation.login.LoginViewModel
import com.parksupark.soomjae.features.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val libsModule = module {
    includes(googleAuthModule)
}

private val emailLoginModule = module {
    viewModelOf(::EmailLoginViewModel)
}

private val emailVerificationModule = module {
    viewModelOf(::EmailVerificationViewModel)
}

private val loginModule = module {
    viewModelOf(::LoginViewModel)
}

private val registerModule = module {
    viewModel { params ->
        RegisterViewModel(
            email = params.get(),
            userDataValidator = get(),
            authRepository = get(),
        )
    }
}

val featuresAuthPresentationModule = module {
    includes(
        libsModule,
        emailLoginModule,
        emailVerificationModule,
        loginModule,
        registerModule,
    )
}
