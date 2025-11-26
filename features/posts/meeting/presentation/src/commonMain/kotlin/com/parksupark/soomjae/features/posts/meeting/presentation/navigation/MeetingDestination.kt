package com.parksupark.soomjae.features.posts.meeting.presentation.navigation

import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import kotlinx.serialization.Serializable

interface MeetingDestination : NavigationDestination {
    @Serializable
    data class MeetingWrite(val postId: Long? = null) : MeetingDestination
}
