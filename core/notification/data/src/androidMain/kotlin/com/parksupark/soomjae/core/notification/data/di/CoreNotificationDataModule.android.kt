package com.parksupark.soomjae.core.notification.data.di

import com.parksupark.soomjae.core.notification.data.service.FirebasePushNotificationService
import com.parksupark.soomjae.core.notification.domain.service.PushNotificationService
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformCoreNotificationModule: Module = module {
    singleOf(::FirebasePushNotificationService).bind<PushNotificationService>()
}
