package com.parksupark.soomjae.features.post.presentation.post.tabs.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.itemKey
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.features.post.presentation.post.models.CommunityPostUi

@Composable
internal fun CommunityTabScreen(
    state: CommunityTabState,
    onAction: (CommunityTabAction) -> Unit,
    posts: LazyPagingItems<CommunityPostUi>,
) {
    SoomjaeScaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            when (val loadState = posts.loadState.refresh) {
                LoadStateLoading -> item { CircularProgressIndicator() }

                is LoadStateNotLoading -> items(count = posts.itemCount, key = posts.itemKey { it.id }) { index ->
                    val post = posts[index]!!
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            post.id,
                            Modifier.weight(1f),
                        )
                    }
                }

                is LoadStateError -> item {
                    Text(loadState.error.message!!)
                }

                else -> error("when should be exhaustive")
            }
        }
    }
}
