package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.utils.ProvideContentColorTextStyle

@Composable
fun PostCard(
    title: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    aboveHeader: @Composable (() -> Unit)? = null,
    header: @Composable (() -> Unit)? = null,
    body: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
) {
    val decoratedAboveHeader: @Composable (() -> Unit)? = aboveHeader?.let {
        {
            ProvideContentColorTextStyle(
                contentColor = SoomjaeTheme.colorScheme.text3,
                textStyle = SoomjaeTheme.typography.labelS,
                content = { it() },
            )
        }
    }

    val decoratedHeader: @Composable (() -> Unit)? = header?.let {
        {
            ProvideTextStyle(
                value = SoomjaeTheme.typography.captionS,
                content = { it() },
            )
        }
    }

    val decoratedTitle: @Composable (() -> Unit) = {
        ProvideTextStyle(
            value = SoomjaeTheme.typography.title3,
            content = { title() },
        )
    }

    val decoratedBody: @Composable (() -> Unit)? = body?.let {
        {
            ProvideTextStyle(
                value = SoomjaeTheme.typography.body2,
                content = { it() },
            )
        }
    }

    val decoratedFooter: @Composable (() -> Unit)? = footer?.let {
        {
            ProvideContentColorTextStyle(
                contentColor = SoomjaeTheme.colorScheme.text4,
                textStyle = SoomjaeTheme.typography.captionS,
                content = { it() },
            )
        }
    }

    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        decoratedAboveHeader?.invoke()

        decoratedHeader?.invoke()

        decoratedTitle()

        decoratedBody?.invoke()

        decoratedFooter?.invoke()
    }
}
