package com.parksupark.soomjae.features.posts.aggregate.presentation.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.aggregate.presentation.post.tabs.member.MemberTabScreen
import com.parksupark.soomjae.features.posts.aggregate.presentation.post.tabs.member.MemberTabState

@Composable
@Preview(name = "MemberTab")
private fun MemberTabScreenPreview() {
    AppTheme {
        MemberTabScreen(
            state = MemberTabState(),
            onAction = {},
        )
    }
}
