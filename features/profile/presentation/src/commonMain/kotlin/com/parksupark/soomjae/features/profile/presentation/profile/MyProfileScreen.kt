package com.parksupark.soomjae.features.profile.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTab
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTabRow
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTopAppBarDefaults
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.profile.presentation.profile.adapter.ScrollableStateAdapter
import com.parksupark.soomjae.features.profile.presentation.profile.adapter.asAdapter
import com.parksupark.soomjae.features.profile.presentation.profile.components.UserProfileCard
import com.parksupark.soomjae.features.profile.presentation.profile.mdoels.UserUi
import com.parksupark.soomjae.features.profile.presentation.profile.tabs.member_post.ProfileMemberPostTab
import com.parksupark.soomjae.features.profile.presentation.resources.Res
import com.parksupark.soomjae.features.profile.presentation.resources.my_profile_login_button
import com.parksupark.soomjae.features.profile.presentation.resources.my_profile_login_request
import com.parksupark.soomjae.features.profile.presentation.resources.profile_tab_introduction
import com.parksupark.soomjae.features.profile.presentation.resources.profile_tab_meetings
import com.parksupark.soomjae.features.profile.presentation.resources.profile_tab_posts
import com.parksupark.soomjae.features.profile.presentation.resources.profile_tab_reviews
import com.parksupark.soomjae.features.profile.presentation.resources.profile_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyProfileScreen(
    bottomBar: @Composable () -> Unit,
    state: ProfileState.MyProfileState,
    onAction: (ProfileAction) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    SoomjaeScaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MyProfileTopBar(onSettingClick = { onAction(ProfileAction.OnSettingClick) }, scrollBehavior = scrollBehavior) },
        bottomBar = bottomBar,
    ) { innerPadding ->
        if (state.isLoggedIn) {
            MyProfileContent(
                user = state.user,
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            GuestProfileContent(
                modifier = Modifier.padding(innerPadding),
                onLoginClick = { onAction(ProfileAction.OnLoginClick) },
            )
        }
    }
}

@Composable
private fun MyProfileContent(
    user: UserUi,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { ProfileTab.entries.size })
    val coroutineScope = rememberCoroutineScope()

    val parentState = rememberLazyListState()
    val memberPostTabGridState = rememberLazyGridState()

    val stubState = rememberLazyListState()
    val nestedScrollConnection = rememberNestedScrollMediator(
        parentState = parentState,
        childAdapterProvider = {
            when (ProfileTab.entries[pagerState.currentPage]) {
                ProfileTab.MEMBER_POSTS -> memberPostTabGridState.asAdapter()
                else -> stubState.asAdapter()
            }
        },
    )
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .nestedScroll(nestedScrollConnection), // 부모 scroll 연결
        state = parentState,
        contentPadding = contentPadding,
    ) {
        // 상단 프로필 카드
        item {
            UserProfileCard(
                user = user,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }

        // sticky TabRow
        stickyHeader {
            ProfileTabRow(
                pagerState = pagerState,
                onTabSelect = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }

        // 각 탭 페이지 (수직 스크롤 가능)
        item {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillParentMaxHeight()
                    .nestedScroll(nestedScrollConnection), // Pager도 chain relay 역할 수행
            ) { page ->
                when (ProfileTab.entries[page]) {
                    ProfileTab.INTRODUCTION -> {
                        LazyColumn(state = stubState, modifier = Modifier.fillMaxSize()) {
                            items(count = 1000) {
                                Spacer(modifier = Modifier.height(16.dp).fillMaxWidth())
                            }
                        }
                    }

                    ProfileTab.MEMBER_POSTS -> ProfileMemberPostTab(userId = user.id, listState = memberPostTabGridState)

                    ProfileTab.MEETING_POSTS -> {
                        // TODO()
                    }

                    ProfileTab.REVIEWS -> {
                        // TODO()
                    }
                }
            }
        }
    }
}

@Composable
fun rememberNestedScrollMediator(
    parentState: LazyListState,
    childAdapterProvider: () -> ScrollableStateAdapter,
): NestedScrollConnection = remember {
    object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            if (source != NestedScrollSource.UserInput) return Offset.Zero

            val dy = available.y
            val child = childAdapterProvider()

            return when {
                dy < 0f -> handleScrollUp(parentState, child, dy)
                dy > 0f -> handleScrollDown(parentState, child, dy)
                else -> Offset.Zero
            }
        }
    }
}

private fun handleScrollUp(
    parent: LazyListState,
    child: ScrollableStateAdapter,
    dy: Float,
): Offset {
    val canParentScrollDown = canScrollDown(parent)
    if (!canParentScrollDown) return Offset.Zero // 헤더 다 사라짐 → 자식 우선

    val consumed = parent.dispatchRawDelta(-dy)
    return Offset(0f, -consumed)
}

private fun handleScrollDown(
    parent: LazyListState,
    child: ScrollableStateAdapter,
    dy: Float,
): Offset {
    // 자식이 위로 스크롤 가능한 상태라면 → 자식이 먼저 소비
    if (child.canScrollUp()) return Offset.Zero

    // 부모가 위로 복귀 가능한 경우만 소비
    if (!canScrollUp(parent)) return Offset.Zero

    val consumed = parent.dispatchRawDelta(-dy)
    return Offset(0f, -consumed)
}

private fun canScrollUp(state: LazyListState): Boolean = !(state.firstVisibleItemIndex == 0 && state.firstVisibleItemScrollOffset == 0)

private fun canScrollDown(state: LazyListState): Boolean {
    val layout = state.layoutInfo
    if (layout.totalItemsCount == 0) return false
    val lastVisibleIndex = layout.visibleItemsInfo.lastOrNull()?.index ?: return false
    // 마지막 아이템이 보이고 & 이미 끝까지 내려간 상태면 false
    val atListEnd = lastVisibleIndex == layout.totalItemsCount - 1 &&
        state.firstVisibleItemScrollOffset >= (layout.viewportEndOffset - layout.viewportStartOffset)
    return !atListEnd
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTabRow(
    pagerState: PagerState,
    onTabSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentPage by remember { derivedStateOf { pagerState.currentPage } }

    SoomjaeTabRow(selectedTabIndex = currentPage, modifier = modifier) {
        ProfileTab.entries.forEachIndexed { index, tab ->
            SoomjaeTab(
                selected = currentPage == index,
                onClick = { onTabSelect(index) },
                text = { Text(tab.title.value) },
            )
        }
    }
}

private enum class ProfileTab(
    val title: StringResource,
) {
    INTRODUCTION(title = Res.string.profile_tab_introduction),
    MEMBER_POSTS(title = Res.string.profile_tab_posts),
    MEETING_POSTS(title = Res.string.profile_tab_meetings),
    REVIEWS(title = Res.string.profile_tab_reviews),
}

@Composable
private fun GuestProfileContent(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LoginRequestSection(onLoginClick = onLoginClick)
        Spacer(modifier = Modifier.weight(2f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyProfileTopBar(
    onSettingClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    SoomjaeCenterAlignedTopAppBar(
        title = { Text(Res.string.profile_title.value) },
        actions = {
            IconButton(
                onClick = onSettingClick,
                content = { Icon(Icons.Outlined.Settings, "") },
            )
        },
        colors = SoomjaeTopAppBarDefaults.topAppBarColors().copy(
            containerColor = SoomjaeTheme.colorScheme.background1,
            scrolledContainerColor = SoomjaeTheme.colorScheme.background1,
        ),
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun LoginRequestSection(onLoginClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.my_profile_login_request),
            style = SoomjaeTheme.typography.labelL.copy(color = SoomjaeTheme.colorScheme.text2),
        )

        SoomjaeButton(
            onClick = onLoginClick,
            content = {
                Icon(Icons.Default.Add, contentDescription = null)

                Text(stringResource(Res.string.my_profile_login_button))
            },
        )
    }
}
