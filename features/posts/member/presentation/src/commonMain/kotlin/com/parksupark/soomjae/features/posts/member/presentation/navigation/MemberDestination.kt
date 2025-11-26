package com.parksupark.soomjae.features.posts.member.presentation.navigation

import com.parksupark.soomjae.core.presentation.ui.navigation.NavigationDestination
import kotlinx.serialization.Serializable

sealed interface MemberDestination : NavigationDestination {
    @Serializable
    data object MemberPostWrite : MemberDestination
}
