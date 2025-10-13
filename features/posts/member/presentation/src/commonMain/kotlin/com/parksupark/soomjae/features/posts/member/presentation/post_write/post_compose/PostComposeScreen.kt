package com.parksupark.soomjae.features.posts.member.presentation.post_write.post_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextField
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.core.presentation.ui.utils.pxToDp
import com.parksupark.soomjae.features.posts.member.presentation.post_write.components.PreviewPhoto
import com.parksupark.soomjae.features.posts.member.presentation.resources.Res
import com.parksupark.soomjae.features.posts.member.presentation.resources.common_back
import com.parksupark.soomjae.features.posts.member.presentation.resources.post_compose_content_hint
import com.parksupark.soomjae.features.posts.member.presentation.resources.post_compose_submit_action
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun PostComposeScreen(
    state: PostComposeState,
    onAction: (PostComposeAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val inputFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        inputFocusRequester.requestFocus()
    }

    Box(modifier = modifier.fillMaxSize()) {
        SoomjaeScaffold(
            topBar = {
                PostComposeTopBar(
                    canSubmit = state.canSubmit,
                    onBackClick = { onAction(PostComposeAction.OnBackClick) },
                    onSubmitClick = { onAction(PostComposeAction.OnSubmitClick) },
                )
            },
        ) { innerPadding ->
            PostComposeContent(
                state = state,
                inputFocusRequester = inputFocusRequester,
                modifier = Modifier.padding(innerPadding),
            )
        }

        if (state.isSubmitting) {
            SubmittingOverlay()
        }
    }
}

@Composable
private fun PostComposeContent(
    state: PostComposeState,
    inputFocusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    val screenWidth = LocalWindowInfo.current.containerSize.width.pxToDp()
    val width = remember(screenWidth) { (screenWidth - 40.dp).coerceAtLeast(160.dp) }
    val height = remember(width) { width }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth().heightIn(height),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) {
                items(
                    items = state.photos,
                    key = { it.localImage.id },
                ) { photo ->
                    PreviewPhoto(
                        photo = photo,
                        modifier = Modifier.size(width),
                        width = width,
                        height = height,
                    )
                }
            }
        }

        item {
            SoomjaeTextField(
                state = state.inputContent,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .focusRequester(inputFocusRequester),
                hint = Res.string.post_compose_content_hint.value,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostComposeTopBar(
    canSubmit: Boolean,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SoomjaeCenterAlignedTopAppBar(
        title = { },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = Res.string.common_back.value,
                )
            }
        },
        actions = {
            SoomjaeTextButton(
                onClick = onSubmitClick,
                enabled = canSubmit,
            ) {
                Text(text = Res.string.post_compose_submit_action.value)
            }
        },
    )
}

@Composable
private fun SubmittingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoomjaeTheme.colorScheme.background4)
            .clickable(enabled = false) { },
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            strokeWidth = 3.dp,
        )
    }
}

@Preview
@Composable
private fun PostComposeScreenPreview() {
    AppTheme {
        PostComposeScreen(
            state = PostComposeState(),
            onAction = { },
        )
    }
}
