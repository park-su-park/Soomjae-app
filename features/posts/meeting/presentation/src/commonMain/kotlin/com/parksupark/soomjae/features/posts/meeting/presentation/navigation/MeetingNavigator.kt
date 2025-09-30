package com.parksupark.soomjae.features.posts.meeting.presentation.navigation

import androidx.navigation.NavHostController
import com.parksupark.soomjae.core.presentation.ui.navigation.SoomjaeNavigator
import com.parksupark.soomjae.core.presentation.ui.navigation.overridden

interface MeetingNavigator : SoomjaeNavigator {
    fun navigateToMeetingWrite()

    fun navigateToMeetingCreate()

    fun navigateToMeetingDetail(postId: Long)

    fun navigateToParticipantList(meetingId: Long) {
        overridden()
    }
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

    override fun navigateToMeetingCreate() {
        overridden()
    }

    override fun navigateToMeetingDetail(postId: Long) {
        overridden()
    }

    override fun navigateToParticipantList(meetingId: Long) {
        overridden()
    }
}

fun soomjaeMeetingNavigator(navController: NavHostController): MeetingNavigator = SoomjaeMeetingNavigator(navController)
