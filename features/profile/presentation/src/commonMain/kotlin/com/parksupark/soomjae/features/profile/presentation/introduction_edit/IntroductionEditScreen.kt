package com.parksupark.soomjae.features.profile.presentation.introduction_edit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.parksupark.soomjae.core.image.presentation.components.UploadAwareImage
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeIcon
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.features.profile.presentation.introduction_edit.components.RichTextStyleRow
import com.parksupark.soomjae.features.profile.presentation.resources.Res
import com.parksupark.soomjae.features.profile.presentation.resources.introduction_edit_content_placeholder
import com.valentinilk.shimmer.shimmer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun IntroductionEditScreen(
    state: IntroductionEditState,
    onAction: (IntroductionEditActions) -> Unit,
) {
    SoomjaeScaffold(
        topBar = {
            IntroductionPostTopBar(
                onBackClick = { onAction(IntroductionEditActions.OnBackClick) },
                canSave = !state.isLoading && !state.isSaving,
                onSaveClick = { onAction(IntroductionEditActions.OnSaveClick) },
            )
        },
    ) { innerPadding ->
        if (state.isLoading) {
            IntroductionPostLoading(
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize(),
            )
        } else {
            IntroductionEditContent(
                state = state,
                innerPadding = innerPadding,
                onAction = onAction,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IntroductionPostTopBar(
    onBackClick: () -> Unit,
    canSave: Boolean,
    onSaveClick: () -> Unit,
) {
    SoomjaeCenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = {
                    SoomjaeIcon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                },
            )
        },
        actions = {
            IconButton(
                onClick = onSaveClick,
                enabled = canSave,
                content = {
                    SoomjaeIcon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = "Save",
                        tint = if (canSave) {
                            SoomjaeTheme.colorScheme.iconColored
                        } else {
                            SoomjaeTheme.colorScheme.iconDisabled
                        },
                    )
                },
            )
        },
    )
}

@Composable
private fun IntroductionPostLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .shimmer(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .background(
                    color = SoomjaeTheme.colorScheme.background3,
                    shape = RoundedCornerShape(8.dp),
                ),
        )
        Spacer(modifier = Modifier.height(16.dp))

        repeat(3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(end = (it + 1) * 40.dp)
                    .background(
                        color = SoomjaeTheme.colorScheme.background3,
                        shape = RoundedCornerShape(8.dp),
                    ),
            )
        }
    }
}

@Composable
private fun IntroductionEditContent(
    state: IntroductionEditState,
    onAction: (IntroductionEditActions) -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val richTextState = state.richTextState
    val uploadingImages = state.imageUploads.filter { it.isUploading }
    val shouldShowUploadingImage = !uploadingImages.isEmpty()

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxSize(),
        contentPadding = innerPadding,
    ) {
        stickyHeader {
            RichTextStyleRow(
                state = richTextState,
                onImageSelect = { onAction(IntroductionEditActions.OnImageSelected(it)) },
            )

            AnimatedVisibility(
                visible = shouldShowUploadingImage,
            ) {
                LazyRow {
                    items(items = uploadingImages, key = { it.id }) { imageUpload ->
                        UploadAwareImage(
                            item = imageUpload,
                            modifier = Modifier.animateItem(),
                            size = 48.dp,
                        )
                    }
                }
            }
        }

        item {
            IntroductionEditInput(
                state = richTextState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .heightIn(min = 200.dp),
            )
        }
    }
}

@Composable
private fun IntroductionEditInput(
    state: RichTextState,
    modifier: Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicRichTextEditor(
        state = state,
        modifier = modifier.onFocusChanged {
            isFocused = it.isFocused
        },
        textStyle = SoomjaeTheme.typography.body1.copy(
            color = SoomjaeTheme.colorScheme.text1,
        ),
        decorationBox = { innerTextField ->
            if (!isFocused && state.toText().isEmpty()) {
                Text(
                    text = Res.string.introduction_edit_content_placeholder.value,
                    color = SoomjaeTheme.colorScheme.text3,
                    style = SoomjaeTheme.typography.body1,
                )
            }

            innerTextField()
        },
        cursorBrush = SolidColor(SoomjaeTheme.colorScheme.primary),
    )
}

@Preview
@Composable
private fun IntroductionEditScreen_LoadingPreview() {
    AppTheme {
        IntroductionEditScreen(
            state = IntroductionEditState(
                isLoading = true,
            ),
            onAction = { },
        )
    }
}

@Preview
@Composable
private fun IntroductionEditScreen_Preview() {
    AppTheme {
        IntroductionEditScreen(
            state = IntroductionEditState(
                isLoading = false,
            ),
            onAction = { },
        )
    }
}
