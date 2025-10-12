package com.parksupark.soomjae.features.posts.member.presentation.post_write.photo_picker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeButton
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeCenterAlignedTopAppBar
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeScaffold
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeTextButton
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.core.presentation.ui.utils.pxToDp
import com.parksupark.soomjae.features.posts.member.presentation.resources.Res
import com.parksupark.soomjae.features.posts.member.presentation.resources.photo_picker_add_action
import com.parksupark.soomjae.features.posts.member.presentation.resources.photo_picker_content_desc
import com.parksupark.soomjae.features.posts.member.presentation.resources.photo_picker_empty_message
import com.parksupark.soomjae.features.posts.member.presentation.resources.photo_picker_remove_desc
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.path
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun PhotoPickerScreen(
    state: PhotoPickerState,
    onAction: (PhotoPickerAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val filePickerLauncher = rememberFilePickerLauncher(
        type = FileKitType.Image,
        mode = FileKitMode.Multiple(),
        onResult = { files ->
            if (!files.isNullOrEmpty()) {
                onAction(PhotoPickerAction.OnSelectPhotos(files))
            }
        },
    )

    SoomjaeScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            PhotoPickerTopBar(
                onBackClick = { onAction(PhotoPickerAction.OnBackClick) },
                onNextClick = { onAction(PhotoPickerAction.OnNextClick) },
                canGoNext = { state.photos.isNotEmpty() },
            )
        },
    ) { innerPadding ->
        PhotoPickerContent(
            state = state,
            onRemove = { onAction(PhotoPickerAction.OnRemovePhoto(it)) },
            onAddClick = { filePickerLauncher.launch() },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
private fun PhotoPickerContent(
    state: PhotoPickerState,
    onRemove: (PlatformFile) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val containerSize = LocalWindowInfo.current.containerSize
    val screenWidth = containerSize.width.pxToDp()
    val (width, height) = remember(containerSize) {
        val width = (screenWidth - 40.dp).coerceAtLeast(160.dp)
        val height = width
        width to height
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state.photos.isEmpty()) {
            PhotoPickerEmptyView(width, height)
        } else {
            PhotoPreviewList(
                photos = state.photos,
                width = width,
                height = height,
                onRemove = onRemove,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SoomjaeButton(onClick = onAddClick) {
            Text(text = Res.string.photo_picker_add_action.value)
        }
    }
}

@Composable
private fun PhotoPickerEmptyView(
    width: Dp,
    height: Dp,
) {
    Box(
        modifier = Modifier.size(width = width, height = height),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = Res.string.photo_picker_empty_message.value,
            style = SoomjaeTheme.typography.headlineM,
        )
    }
}

@Composable
private fun PhotoPreviewList(
    photos: ImmutableList<PlatformFile>,
    width: Dp,
    height: Dp,
    onRemove: (PlatformFile) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(items = photos, key = { it.path }) { platformFile ->
            Box {
                AsyncImage(
                    model = platformFile,
                    contentDescription = Res.string.photo_picker_content_desc.value,
                    modifier = Modifier.size(width = width, height = height),
                    contentScale = ContentScale.Crop,
                )
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = Res.string.photo_picker_remove_desc.value,
                    tint = SoomjaeTheme.colorScheme.icon.copy(alpha = 0.6f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                        .minimumInteractiveComponentSize()
                        .clickable { onRemove(platformFile) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhotoPickerTopBar(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    canGoNext: () -> Boolean,
    modifier: Modifier = Modifier,
) {
    SoomjaeCenterAlignedTopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "개시글 작성 취소",
                )
            }
        },
        actions = {
            SoomjaeTextButton(
                onClick = onNextClick,
                enabled = canGoNext(),
                content = {
                    Text(text = "다음")
                },
            )
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun PhotoPickerScreenPreview() {
    AppTheme {
        PhotoPickerScreen(
            state = PhotoPickerState(),
            onAction = { },
        )
    }
}
