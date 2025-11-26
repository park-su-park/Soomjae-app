package com.parksupark.soomjae.core.image.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.parksupark.soomjae.core.image.domain.models.UploadProgress
import com.parksupark.soomjae.core.image.presentation.model.PhotoUploadItem
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

@Composable
fun UploadAwareImage(
    item: PhotoUploadItem,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    size: Dp = 96.dp,
    cornerRadius: Dp = 12.dp,
    overlayAlpha: Float = 0.45f,
    successVisibleDuration: Duration = 3.seconds,
    onRetry: ((PhotoUploadItem) -> Unit)? = null,
) {
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .size(size)
            .clip(shape),
    ) {
        UploadImage(item = item, shape = shape, contentDescription = contentDescription)

        when (val progress = item.uploadProgress) {
            UploadProgress.Idle -> Unit
            is UploadProgress.InProgress ->
                UploadProgressLayer(progress = progress, overlayAlpha = overlayAlpha)

            is UploadProgress.Success ->
                UploadSuccessLayer(
                    overlayAlpha = overlayAlpha,
                    duration = successVisibleDuration,
                )

            is UploadProgress.Error ->
                UploadErrorLayer(
                    item = item,
                    onRetry = onRetry,
                    overlayAlpha = overlayAlpha,
                )
        }
    }
}

@Composable
private fun UploadImage(
    item: PhotoUploadItem,
    shape: Shape,
    contentDescription: String?,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(item.localImage.bytes)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = Modifier
            .fillMaxSize()
            .clip(shape),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun UploadProgressLayer(
    progress: UploadProgress.InProgress,
    overlayAlpha: Float,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = overlayAlpha)),
    ) {
        CircularProgressIndicator(
            progress = { progress.fraction },
            modifier = Modifier.size(42.dp).align(Alignment.Center),
            strokeWidth = 4.dp,
        )
    }
}

@Composable
private fun UploadSuccessLayer(
    overlayAlpha: Float,
    duration: Duration,
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(duration)
        visible = false
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = overlayAlpha / 2f)),
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = SoomjaeTheme.colorScheme.success,
                modifier = Modifier.size(40.dp).align(Alignment.Center),
            )
        }
    }
}

@Composable
private fun UploadErrorLayer(
    item: PhotoUploadItem,
    onRetry: ((PhotoUploadItem) -> Unit)?,
    overlayAlpha: Float,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = overlayAlpha)),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                tint = SoomjaeTheme.colorScheme.error,
                modifier = Modifier.size(36.dp),
            )

            if (onRetry != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    "Retry",
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0x33FFFFFF))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .clickable { onRetry(item) },
                )
            }
        }
    }
}
