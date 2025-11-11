package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun PostWriteLayout(
    title: @Composable () -> Unit,
    body: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    extras: @Composable (ColumnScope.() -> Unit)? = null,
    scrollable: Boolean = true,
) {
    val decoratedTitle: @Composable (() -> Unit) = {
        ProvideTextStyle(
            value = SoomjaeTheme.typography.title2,
            content = { title() },
        )
    }

    val decoratedBody: @Composable (() -> Unit) = {
        ProvideTextStyle(
            value = SoomjaeTheme.typography.body1,
            content = { body() },
        )
    }

    val contentModifier = if (scrollable) {
        modifier.verticalScroll(rememberScrollState())
    } else {
        modifier
    }

    Column(
        modifier = contentModifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        decoratedTitle()

        SoomjaeHorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        decoratedBody()

        extras?.invoke(this)
    }
}

@Composable
fun LazyPostWriteLayout(
    title: @Composable () -> Unit,
    body: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    extras: (LazyListScope.() -> Unit)? = null,
) {
    val decoratedTitle: @Composable (() -> Unit) = {
        ProvideTextStyle(
            value = SoomjaeTheme.typography.title2,
            content = { title() },
        )
    }

    val decoratedBody: @Composable (() -> Unit) = {
        ProvideTextStyle(
            value = SoomjaeTheme.typography.body1,
            content = { body() },
        )
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = listState,
        contentPadding = contentPadding,
    ) {
        item {
            decoratedTitle()

            SoomjaeHorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            )

            decoratedBody()
        }

        extras?.invoke(this)
    }
}
