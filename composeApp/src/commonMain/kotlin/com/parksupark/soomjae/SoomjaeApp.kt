@file:OptIn(KoinExperimentalAPI::class)

package com.parksupark.soomjae

import SoomJae.composeApp.BuildConfig
import androidx.compose.runtime.Composable
import com.parksupark.soomjae.di.soomjaeModule
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import org.koin.dsl.KoinConfiguration

@Composable
fun Soomjae() {
    KoinMultiplatformApplication(
        config = KoinConfiguration { modules(soomjaeModule) },
        logLevel = if (BuildConfig.isDebug) Level.DEBUG else Level.NONE,
    ) {
        App()
    }
}
