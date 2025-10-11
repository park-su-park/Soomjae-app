package com.parksupark.soomjae.features.posts.member.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden

interface MemberNavigator : SoomjaeNavigator {
    fun navigateToMemberWrite()
}

private class SoomjaeMemberNavigator(
    override val navController: NavHostController,
) : MemberNavigator {
    override fun navigateBack() {
        overridden()
    }

    override fun navigateToMemberWrite() {
        overridden()
    }
}

fun soomjaeMemberNavigator(navController: NavHostController): MemberNavigator = SoomjaeMemberNavigator(navController = navController)
