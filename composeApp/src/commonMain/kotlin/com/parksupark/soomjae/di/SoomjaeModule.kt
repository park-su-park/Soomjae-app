package com.parksupark.soomjae.di

import com.parksupark.soomjae.core.common.di.coreCommonModule
import com.parksupark.soomjae.core.remote.di.coreRemoteModule
import org.koin.dsl.module

internal val soomjaeModule = module {
    includes(coreCommonModule)
    includes(coreRemoteModule)
}
