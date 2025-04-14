package com.parksupark.soomjae.core.remote.di

import com.parksupark.soomjae.core.remote.networking.HttpClientFactory
import org.koin.dsl.module

val coreRemoteModule = module {
    single {
        HttpClientFactory().build()
    }
}
