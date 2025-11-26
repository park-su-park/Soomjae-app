package com.parksupark.soomjae.core.presentation.ui.navigation

import androidx.navigation.NavHostController
import co.touchlab.kermit.Logger

private const val LOG_TAG = "SoomjaeNavigator"

interface SoomjaeNavigator {
    val navController: NavHostController

    fun navigateBack()
}

@Suppress("UnusedReceiverParameter")
fun SoomjaeNavigator.overridden(navigator: String = "SoomjaeRootNavigator") {
    Logger.i(LOG_TAG) { "Implementation overridden in $navigator" }
}
