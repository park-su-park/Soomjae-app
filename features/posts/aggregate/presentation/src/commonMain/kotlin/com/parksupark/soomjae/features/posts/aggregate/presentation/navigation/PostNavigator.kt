package com.parksupark.soomjae.features.posts.aggregate.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden
import com.parksupark.soomjae.features.posts.community.presentation.navigation.CommunityNavigator
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.MeetingNavigator
import com.parksupark.soomjae.features.posts.meeting.presentation.navigation.soomjaeMeetingNavigator
import com.parksupark.soomjae.features.posts.member.presentation.navigation.MemberNavigator
import com.parksupark.soomjae.features.posts.member.presentation.navigation.soomjaeMemberNavigator

interface PostNavigator : SoomjaeNavigator, CommunityNavigator, MeetingNavigator, MemberNavigator {
    override fun navigateToCommunityWrite()

    override fun navigateToCommunityDetail(postId: Long)
}

private class SoomjaePostNavigator(
    override val navController: NavHostController,
    meetingNavigator: MeetingNavigator,
    memberNavigator: MemberNavigator,
) : PostNavigator,
    MeetingNavigator by meetingNavigator,
    MemberNavigator by memberNavigator {

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

    override fun navigateToMeetingDetail(postId: Long) {
        overridden()
    }

    override fun navigateToParticipantList(meetingId: Long) {
        overridden()
    }

    override fun navigateToMemberWrite() {
        overridden()
    }
}

fun soomjaePostNavigator(navController: NavHostController): PostNavigator = SoomjaePostNavigator(
    navController = navController,
    meetingNavigator = soomjaeMeetingNavigator(navController),
    memberNavigator = soomjaeMemberNavigator(navController),
)
