package com.parksupark.soomjae.features.auth.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden

interface AuthNavigator : SoomjaeNavigator {
    fun navigateToRegister()

    fun navigateToRegisterDetail(email: String)

    fun navigateToEmailLogin(email: String? = null)

    fun popUpAuthGraph() {
        overridden()
    }
}

private class SoomjaeAuthNavigator(
    override val navController: NavHostController,
) : AuthNavigator {
    override fun navigateBack() {
        overridden()
    }

    override fun navigateToRegister() {
        overridden()
    }

    override fun navigateToRegisterDetail(email: String) {
        overridden()
    }

    override fun navigateToEmailLogin(email: String?) {
        overridden()
    }

    override fun popUpAuthGraph() {
        overridden()
    }
}

fun soomjaeAuthNavigator(navController: NavHostController): AuthNavigator = SoomjaeAuthNavigator(navController)
