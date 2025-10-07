package com.parksupark.soomjae.features.posts.aggregate.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden
import com.parksupark.soomjae.features.posts.community.presentation.navigation.CommunityNavigator
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.soomjaeMeetingNavigator

interface PostNavigator : SoomjaeNavigator, CommunityNavigator, MeetingNavigator {
    override fun navigateToCommunityWrite()

    override fun navigateToCommunityDetail(postId: Long)

    fun navigateToMemberWrite()
}

private class SoomjaePostNavigator(
    override val navController: NavHostController,
    meetingNavigator: MeetingNavigator,
) : PostNavigator,
    MeetingNavigator by meetingNavigator {

    override fun navigateBack() {
        overridden()
    }

    override fun navigateToCommunityWrite() {
        overridden()
    }

    override fun navigateToCommunityDetail(postId: Long) {
        overridden()
    }

    override fun navigateToMeetingWrite() {
        overridden()
    }

    override fun navigateToMeetingCreate() {
        overridden()
    }

    override fun navigateToMeetingDetail(postId: Long) {
        overridden()
    }

    override fun navigateToMemberWrite() {
        overridden()
    }
}

fun soomjaePostNavigator(navController: NavHostController): PostNavigator = SoomjaePostNavigator(
    navController = navController,
    meetingNavigator = soomjaeMeetingNavigator(navController),
)
