package com.parksupark.soomjae.features.profile.presentation.profile.adapter

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState

interface ScrollableStateAdapter {
    fun canScrollUp(): Boolean

    fun canScrollDown(): Boolean
}

/** LazyColumn 용 */
fun LazyListState.asAdapter(): ScrollableStateAdapter = object : ScrollableStateAdapter {
    override fun canScrollUp(): Boolean {
        // 첫 아이템이 0이고, 스크롤 오프셋도 0이면 더 위로 못 감
        return !(firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset == 0)
    }

    override fun canScrollDown(): Boolean {
        val layout = layoutInfo
        if (layout.totalItemsCount == 0) return false

        val lastVisible = layout.visibleItemsInfo.lastOrNull() ?: return false
        val lastVisibleIndex = lastVisible.index

        // 아이템의 "아래 끝" 위치 = offset + size (수직 리스트)
        val lastItemEnd = lastVisible.offset + lastVisible.size

        // 뷰포트의 "아래 끝" 위치
        val viewportEnd = layout.viewportEndOffset

        // 마지막 아이템이 화면에 보이고, 그 아이템의 끝이 뷰포트 끝에 닿았거나 넘어섰다면 더 내려갈 곳이 없음
        val atListEnd = lastVisibleIndex == layout.totalItemsCount - 1 && lastItemEnd <= viewportEnd
        return !atListEnd
    }
}

/** LazyVerticalGrid 용 */
fun LazyGridState.asAdapter(): ScrollableStateAdapter = object : ScrollableStateAdapter {
    override fun canScrollUp(): Boolean = !(firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset == 0)

    override fun canScrollDown(): Boolean {
        val layout = layoutInfo
        if (layout.totalItemsCount == 0) return false

        val lastVisible = layout.visibleItemsInfo.lastOrNull() ?: return false
        val lastVisibleIndex = lastVisible.index

        // 그리드에서도 수직 스크롤 기준으로 y축 끝 계산
        val lastItemEnd = lastVisible.offset.y + lastVisible.size.height
        val viewportEnd = layout.viewportEndOffset

        val atGridEnd = lastVisibleIndex == layout.totalItemsCount - 1 && lastItemEnd <= viewportEnd
        return !atGridEnd
    }
}
