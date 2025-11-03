package com.parksupark.soomjae.core.notification.data.di

import com.parksupark.soomjae.core.notification.data.service.KtorDeviceTokenService
import com.parksupark.soomjae.core.notification.domain.service.DeviceTokenService
import org.koin.core.module.Module
import org.koin.dsl.module

private val serviceModule: Module = module {
    single<DeviceTokenService> {
        KtorDeviceTokenService(
            httpClient = get(),
        )
    }
}

val coreNotificationDataModule = module {
    includes(serviceModule)
}
