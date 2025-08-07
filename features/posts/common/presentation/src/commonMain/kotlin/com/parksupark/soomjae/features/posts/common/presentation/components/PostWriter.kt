package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeHorizontalDivider
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun PostWriter(
    title: @Composable () -> Unit,
    body: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    extras: @Composable (ColumnScope.() -> Unit)? = null,
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

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        decoratedTitle()

        SoomjaeHorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = SoomjaeTheme.colorScheme.divider1,
        )

        decoratedBody()

        extras?.invoke(this)
    }
}
