package com.parksupark.soomjae.features.posts.member.presentation.post_write.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.parksupark.soomjae.core.image.domain.models.UploadProgress
import com.parksupark.soomjae.core.image.presentation.model.PhotoUploadItem
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.core.presentation.ui.utils.pxToDp
import com.parksupark.soomjae.features.posts.member.presentation.resources.Res
import com.parksupark.soomjae.features.posts.member.presentation.resources.common_error

@Composable
fun PreviewPhoto(
    photo: PhotoUploadItem,
    modifier: Modifier = Modifier,
    width: Dp? = null,
    height: Dp? = null,
) {
    val containerSize = LocalWindowInfo.current.containerSize
    val screenWidth = containerSize.width.pxToDp()
    val (defaultWidth, defaultHeight) = remember(containerSize) {
        val width = (screenWidth - 40.dp).coerceAtLeast(160.dp)
        val height = width
        width to height
    }

    Box(modifier = modifier) {
        AsyncImage(
            model = photo.localImage.bytes,
            contentDescription = null,
            modifier = Modifier.size(
                width = width ?: defaultWidth,
                height = height ?: defaultHeight,
            ),
            contentScale = ContentScale.Crop,
        )

        when (val progress = photo.uploadProgress) {
            is UploadProgress.InProgress -> CircularProgressIndicator(
                progress = { progress.fraction },
                modifier = Modifier.align(Alignment.Center),
                color = SoomjaeTheme.colorScheme.cta,
                trackColor = SoomjaeTheme.colorScheme.background2,
            )

            is UploadProgress.Error -> Icon(
                imageVector = Icons.Default.Error,
                contentDescription = Res.string.common_error.value,
                tint = SoomjaeTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center),
            )

            else -> Unit
        }
    }
}
