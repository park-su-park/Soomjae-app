package com.parksupark.soomjae.features.setting.presentation.di

import com.parksupark.soomjae.features.setting.presentation.setting.SettingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val settingModule = module {
    viewModelOf(::SettingViewModel)
}

val featuresSettingPresentationModule = module {
    includes(settingModule)
}
