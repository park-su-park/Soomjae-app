package com.parksupark.soomjae.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationBarItem
import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import com.parksupark.soomjae.core.presentation.ui.navigation.loggerObserver
import com.parksupark.soomjae.core.presentation.ui.utils.hasRoute
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthDestination
import com.parksupark.soomjae.features.auth.presentation.navigation.AuthNavigator
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToEmailLogin
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToLogin
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToRegister
import com.parksupark.soomjae.features.auth.presentation.navigation.navigateToRegisterDetail
import com.parksupark.soomjae.features.auth.presentation.navigation.soomjaeAuthNavigator
import com.parksupark.soomjae.features.home.presentation.navigation.HomeNavigator
import com.parksupark.soomjae.features.home.presentation.navigation.soomjaeHomeNavigator
import com.parksupark.soomjae.features.posts.aggregate.presentation.navigation.PostNavigator
import com.parksupark.soomjae.features.posts.aggregate.presentation.navigation.navigateToCommunityDetail
import com.parksupark.soomjae.features.posts.aggregate.presentation.navigation.navigateToMeetingDetail
import com.parksupark.soomjae.features.posts.aggregate.presentation.navigation.navigateToMeetingWrite
import com.parksupark.soomjae.features.posts.aggregate.presentation.navigation.navigateToMemberWrite
import com.parksupark.soomjae.features.posts.aggregate.presentation.navigation.navigateToParticipantList
import com.parksupark.soomjae.features.posts.aggregate.presentation.navigation.soomjaePostNavigator
import com.parksupark.soomjae.features.posts.community.presentation.navigation.navigateToCommunityCreate
import com.parksupark.soomjae.features.posts.community.presentation.navigation.navigateToCommunityEdit
import com.parksupark.soomjae.features.profile.presentation.navigation.ProfileNavigator
import com.parksupark.soomjae.features.profile.presentation.navigation.soomjaeProfileNavigator
import com.parksupark.soomjae.features.setting.presentation.navigation.SettingNavigator
import com.parksupark.soomjae.features.setting.presentation.navigation.navigateToSetting
import com.parksupark.soomjae.features.setting.presentation.navigation.soomjaeSettingNavigator

internal sealed interface RootNavigator :
    AuthNavigator,
    HomeNavigator,
    PostNavigator,
    ProfileNavigator,
    SettingNavigator {
    fun onNavigationBarItemClicked(item: NavigationBarItem)
}

private class SoomjaeRootNavigator(
    override val navController: NavHostController,
    authNavigator: AuthNavigator,
    homeNavigator: HomeNavigator,
    postNavigator: PostNavigator,
    profileNavigator: ProfileNavigator,
    settingNavigator: SettingNavigator,
) : RootNavigator,
    AuthNavigator by authNavigator,
    HomeNavigator by homeNavigator,
    PostNavigator by postNavigator,
    ProfileNavigator by profileNavigator,
    SettingNavigator by settingNavigator {
    override fun navigateBack() {
        navController.navigateUp()
    }

    override fun onNavigationBarItemClicked(item: NavigationBarItem) {
        onNavigationBarItemClicked(item.route)
    }

    // <editor-fold desc="AuthNavigator">
    override fun navigateToRegister() {
        navController.navigateToRegister()
    }

    override fun navigateToRegisterDetail(email: String) {
        navController.navigateToRegisterDetail(email)
    }

    override fun navigateToEmailLogin(email: String?) {
        navController.navigateToEmailLogin(email)
    }

    override fun popUpAuthGraph() {
        navController.popBackStack(AuthDestination.Root, inclusive = true)
    }
    // </editor-fold>

    // <editor-fold desc="PostNavigator">
    override fun navigateToCommunityCreate() {
        navController.navigateToCommunityCreate()
    }

    override fun navigateToCommunityEdit(postId: Long) {
        navController.navigateToCommunityEdit(postId)
    }

    override fun navigateToCommunityDetail(postId: Long) {
        navController.navigateToCommunityDetail(postId)
    }

    override fun navigateToMeetingWrite() {
        navController.navigateToMeetingWrite()
    }

    override fun navigateToMeetingDetail(postId: Long) {
        navController.navigateToMeetingDetail(postId)
    }

    override fun navigateToParticipantList(meetingId: Long) {
        navController.navigateToParticipantList(meetingId)
    }

    override fun navigateToMemberWrite() {
        navController.navigateToMemberWrite()
    }
    // </editor-fold>

    // <editor-fold desc="ProfileNavigator">
    override fun navigateToLogin() {
        navController.navigateToLogin()
    }

    override fun navigateToSetting() {
        navController.navigateToSetting()
    }
    // </editor-fold>

    private fun onNavigationBarItemClicked(screen: NavigationDestination) {
        val hasItemRoute = navController.currentBackStackEntry.hasRoute(screen::class)

        if (!hasItemRoute) {
            navController.navigate(screen) {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

@Composable
internal fun rememberSoomjaeNavigator(): RootNavigator {
    val navController = rememberNavController().loggerObserver()

    return remember {
        SoomjaeRootNavigator(
            navController = navController,
            authNavigator = soomjaeAuthNavigator(navController),
            homeNavigator = soomjaeHomeNavigator(navController),
            postNavigator = soomjaePostNavigator(navController),
            profileNavigator = soomjaeProfileNavigator(navController),
            settingNavigator = soomjaeSettingNavigator(navController),
        )
    }
}
