package com.parksupark.soomjae.di

import com.parksupark.soomjae.core.common.di.coreCommonModule
import org.koin.dsl.module

internal val soomjaeModule = module {
    includes(coreCommonModule)
}
