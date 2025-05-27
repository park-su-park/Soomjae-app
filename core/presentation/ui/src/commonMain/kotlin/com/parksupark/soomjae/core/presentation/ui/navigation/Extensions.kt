package com.parksupark.soomjae.core.presentation.ui.navigation

import SoomJae.core.presentation.ui.BuildConfig
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import co.touchlab.kermit.Logger

@Composable
fun NavHostController.loggerObserver() = apply {
    if (BuildConfig.isDebug) {
        DisposableEffect(this, LocalLifecycleOwner.current.lifecycle) {
            val listener = NavController.OnDestinationChangedListener { navController, destination, args ->
                Logger.d("Navigation") {
                    buildString {
                        append("Navigating to route ${destination.route}")

                        navController.previousBackStackEntry?.destination?.route?.let { previousRoute ->
                            append(" from $previousRoute")
                        }
                    }
                }
            }

            addOnDestinationChangedListener(listener)
            onDispose { removeOnDestinationChangedListener(listener) }
        }
    }
}
