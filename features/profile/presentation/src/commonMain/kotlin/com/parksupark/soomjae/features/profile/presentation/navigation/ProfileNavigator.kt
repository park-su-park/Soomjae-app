package com.parksupark.soomjae.features.profile.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden

interface ProfileNavigator : SoomjaeNavigator {
    fun navigateToLogin()

    fun navigateToSetting()

    fun navigateToProfileEdit(memberId: Long)

    fun navigateToIntroductionEdit(memberId: Long)
}

private class SoomjaeProfileNavigator(
    override val navController: NavHostController,
) : ProfileNavigator {
    override fun navigateBack() {
        overridden()
    }

    override fun navigateToLogin() {
        overridden()
    }

    override fun navigateToSetting() {
        overridden()
    }

    override fun navigateToProfileEdit(memberId: Long) {
        overridden()
    }

    override fun navigateToIntroductionEdit(memberId: Long) {
        overridden()
    }
}

fun soomjaeProfileNavigator(navController: NavHostController): ProfileNavigator =
    SoomjaeProfileNavigator(navController)
