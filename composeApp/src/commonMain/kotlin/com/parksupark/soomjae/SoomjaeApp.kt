@file:OptIn(KoinExperimentalAPI::class)

package com.parksupark.soomjae

import androidx.compose.runtime.Composable
import com.parksupark.soomjae.composeApp.BuildConfig
import com.parksupark.soomjae.di.soomjaeModule
import com.parksupark.soomjae.theme.AppTheme
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
        AppTheme {
            App()
        }
    }
}
