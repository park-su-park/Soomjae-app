package com.parksupark.soomjae.features.posts.common.presentation.tab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemKey
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.features.posts.common.presentation.tab.components.MeetingPostCard
import com.parksupark.soomjae.features.posts.common.presentation.tab.models.MeetingPostUi

@Composable
internal fun MeetingTabScreen(
    state: MeetingTabState,
    onAction: (MeetingTabAction) -> Unit,
    posts: LazyPagingItems<MeetingPostUi>,
) {
    SoomjaeScaffold(modifier = Modifier.fillMaxSize()) { _ ->
        LazyColumn {
            items(count = posts.itemCount, key = posts.itemKey { it.id }) { index ->
                posts[index]?.let { post ->
                    MeetingPostCard(
                        post = post,
                        modifier = Modifier.fillMaxSize(),
                    )
                    SoomjaeHorizontalDivider()
                }
            }
        }
    }
}
