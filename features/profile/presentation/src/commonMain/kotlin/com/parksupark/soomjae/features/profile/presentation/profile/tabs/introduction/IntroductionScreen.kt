package com.parksupark.soomjae.features.profile.presentation.profile.tabs.introduction

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composeunstyled.Text
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichText
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaePullToRefreshBox
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ProfileIntroductionScreen(
    state: IntroductionState,
    onAction: (IntroductionAction) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState,
) {
    val isRefreshing = state.isRefreshing
    val isMe = state.isMe
    val introduction = state.content

    SoomjaePullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { onAction(IntroductionAction.OnPullToRefresh) },
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            if (isMe) {
                item {
                    IntroductionEditButton(
                        onClick = {
                            onAction(IntroductionAction.OnEditClick)
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        hasIntroduction = (introduction != null),
                    )
                }

                if (introduction == null) {
                    item {
                        Text(
                            text = "아직 작성한 소개글이 없어요.\n소개글을 작성해보세요!",
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                } else {
                    item {
                        IntroductionContent(introduction)
                    }
                }
            }
        }
    }
}

@Composable
private fun IntroductionEditButton(
    onClick: () -> Unit,
    modifier: Modifier,
    hasIntroduction: Boolean,
) {
    SoomjaeTextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = if (hasIntroduction) {
                "소개글 수정하기"
            } else {
                "소개글 작성하기"
            },
            color = SoomjaeTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun IntroductionContent(content: HtmlContent) {
    val textState = rememberRichTextState()

    LaunchedEffect(content) {
        textState.setText(content)
    }

    BasicRichText(
        state = textState,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
    )
}

@Composable
@Preview(name = "Introduction")
private fun IntroductionScreenPreview() {
    AppTheme {
        ProfileIntroductionScreen(
            state = IntroductionState(),
            onAction = { },
            listState = rememberLazyListState(),
        )
    }
}
