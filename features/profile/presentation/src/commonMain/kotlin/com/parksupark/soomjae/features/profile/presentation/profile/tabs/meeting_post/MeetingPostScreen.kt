package com.parksupark.soomjae.features.profile.presentation.profile.tabs.meeting_post

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ProfileMeetingPostScreen(
    state: MeetingPostState,
    onAction: (MeetingPostAction) -> Unit,
    listState: LazyListState,
) {
    val isRefreshing = state.isRefreshing

    SoomjaePullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { onAction(MeetingPostAction.OnPullToRefresh) },
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
        }
    }
}

@Composable
@Preview
private fun MeetingPostScreenPreview() {
    ProfileMeetingPostScreen(
        state = MeetingPostState(),
        onAction = { },
        listState = rememberLazyListState(),
    )
}
