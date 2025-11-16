package com.parksupark.soomjae.core.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeDrawable
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.resources.value
import com.parksupark.soomjae.core.presentation.ui.utils.imageRequest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ProfileImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    placeholderRes: DrawableResource = SoomjaeDrawable.ic_sample_image,
    shape: Shape = RoundedCornerShape(24),
    contentScale: ContentScale = ContentScale.Crop,
) {
    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        ProfileImagePreviewContent(
            modifier = modifier,
            size = size,
            placeholderRes = placeholderRes,
            contentScale = contentScale,
            shape = shape,
        )
    } else {
        ProfileImageRuntimeContent(
            imageUrl = imageUrl,
            modifier = modifier,
            size = size,
            placeholderRes = placeholderRes,
            contentScale = contentScale,
            shape = shape,
        )
    }
}

@Composable
private fun ProfileImagePreviewContent(
    size: Dp,
    placeholderRes: DrawableResource,
    shape: Shape,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = placeholderRes.value,
        contentDescription = null,
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(SoomjaeTheme.colorScheme.background2)
            .padding(8.dp),
        contentScale = contentScale,
    )
}

@Composable
private fun ProfileImageRuntimeContent(
    imageUrl: String?,
    size: Dp,
    placeholderRes: DrawableResource,
    shape: Shape,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = imageRequest { data(imageUrl) },
        contentDescription = null,
        modifier = modifier
            .size(size)
            .clip(shape),
        placeholder = placeholderRes.value,
        error = placeholderRes.value,
        contentScale = contentScale,
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileImagePreview(modifier: Modifier = Modifier) {
    AppTheme {
        ProfileImage(
            imageUrl = null,
            modifier = modifier,
        )
    }
}
