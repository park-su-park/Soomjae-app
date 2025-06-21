package com.parksupark.soomjae.core.presentation.ui.di

import com.parksupark.soomjae.core.presentation.ui.controllers.SoomjaeEventController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val controllerModule = module {
    singleOf(::SoomjaeEventController)
}

val corePresentationUiModule = module {
    includes(controllerModule)
}
