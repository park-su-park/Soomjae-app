package com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ProfileMemberPostScreen(
    state: ProfileMemberPostState,
    onAction: (ProfileMemberPostAction) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState,
) {
    LazyColumn(modifier.fillMaxSize(), state = listState) {
        items(count = 100) { index ->
            Text("Item #$index")
        }
    }
}

@Composable
@Preview(name = "ProfileMemberPost")
private fun ProfileMemberPostScreenPreview() {
    ProfileMemberPostScreen(
        state = ProfileMemberPostState(),
        onAction = {},
        listState = rememberLazyListState(),
    )
}
