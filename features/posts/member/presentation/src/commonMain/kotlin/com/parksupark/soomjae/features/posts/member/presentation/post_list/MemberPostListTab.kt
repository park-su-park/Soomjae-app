package com.parksupark.soomjae.features.posts.member.presentation.post_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.common.presentation.components.WritePostFab
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MemberPostListTab(
    state: MemberPostListState,
    onAction: (MemberPostListAction) -> Unit,
    onPostAction: (PostAction) -> Unit,
) {
    SoomjaeScaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        SoomjaePullToRefreshBox(
            isRefreshing = state.isPostsRefreshing,
            onRefresh = { onAction(MemberPostListAction.OnPullToRefresh) },
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {
            WriteMeetingPostFab(
                onWriteClick = { onAction(MemberPostListAction.OnWritePostClick) },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
private fun WriteMeetingPostFab(
    onWriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WritePostFab(onClick = onWriteClick, modifier = modifier.padding(8.dp))
}

@Composable
@Preview(name = "MemberPostList")
private fun MemberPostListScreenPreview() {
    AppTheme {
        MemberPostListTab(
            state = MemberPostListState(),
            onAction = { },
            onPostAction = { },
        )
    }
}
