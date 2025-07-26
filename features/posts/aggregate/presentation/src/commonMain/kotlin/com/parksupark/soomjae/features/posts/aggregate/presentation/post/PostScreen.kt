package com.parksupark.soomjae.features.posts.aggregate.presentation.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTab
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTabRow
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.posts.aggregate.presentation.post.tabs.meeting.MeetingTabRoute
import com.parksupark.soomjae.features.posts.aggregate.presentation.post.tabs.member.MemberTabRoute
import com.parksupark.soomjae.features.posts.aggregate.presentation.resources.Res
import com.parksupark.soomjae.features.posts.aggregate.presentation.resources.post_community_title
import com.parksupark.soomjae.features.posts.aggregate.presentation.resources.post_feed_title
import com.parksupark.soomjae.features.posts.aggregate.presentation.resources.post_meeting_title
import com.parksupark.soomjae.features.posts.common.presentation.PostAction
import com.parksupark.soomjae.features.posts.community.presentation.tab.CommunityTabRoute
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PostScreen(
    state: PostState,
    onAction: (PostAction) -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { PostTabs.entries.size })
    val selectedTabIndex by remember { derivedStateOf { pagerState.currentPage } }

    SoomjaeScaffold(bottomBar = bottomBar) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            SoomjaeTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth(),
            ) {
                PostTabs.entries.forEachIndexed { index, currentTab ->
                    SoomjaeTab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(currentTab.ordinal)
                            }
                        },
                        text = { Text(currentTab.title.value) },
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                when (PostTabs.entries[it]) {
                    PostTabs.COMMUNITY -> CommunityTabRoute(onPostAction = onAction)

                    PostTabs.MEETING -> MeetingTabRoute()

                    PostTabs.FEED -> MemberTabRoute()
                }
            }
        }
    }
}

private enum class PostTabs(
    val title: StringResource,
) {
    COMMUNITY(title = Res.string.post_community_title),
    MEETING(title = Res.string.post_meeting_title),
    FEED(title = Res.string.post_feed_title),
}
