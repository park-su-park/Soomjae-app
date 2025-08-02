package com.parksupark.soomjae.features.posts.common.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden

interface MeetingNavigator : SoomjaeNavigator {
    fun navigateToMeetingWrite()
}

private class SoomjaeMeetingNavigator(
    override val navController: NavHostController,
) : MeetingNavigator {
    override fun navigateBack() {
        overridden()
    }

    override fun navigateToMeetingWrite() {
        overridden()
    }
}

fun soomjaeMeetingNavigator(navController: NavHostController): MeetingNavigator = SoomjaeMeetingNavigator(navController)
