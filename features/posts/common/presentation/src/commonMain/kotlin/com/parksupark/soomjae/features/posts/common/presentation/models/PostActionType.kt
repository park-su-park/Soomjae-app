package com.parksupark.soomjae.features.posts.common.presentation.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.like

sealed class PostActionType(
    open val icon: ImageVector,
    open val selectedIcon: ImageVector? = null,
    open val description: String,
) {
    data object Like : PostActionType(
        icon = Icons.Outlined.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite,
        description = "Like",
    )

    data object Comment : PostActionType(
        icon = Icons.AutoMirrored.Outlined.Comment,
        description = "Comment",
    )

    data object Share : PostActionType(
        icon = Icons.AutoMirrored.Outlined.Send,
        description = "Share",
    )

    data class Custom(
        override val icon: ImageVector,
        override val selectedIcon: ImageVector? = null,
        override val description: String,
    ) : PostActionType(icon, selectedIcon, description)
}

@Composable
fun PostActionType.resolveVisuals(isSelected: Boolean): Pair<ImageVector, Color> {
    val colorScheme = SoomjaeTheme.colorScheme

    val icon = if (isSelected) selectedIcon ?: this.icon else this.icon
    val tint = when (this) {
        PostActionType.Like -> if (isSelected) colorScheme.like else colorScheme.icon
        else -> colorScheme.icon
    }
    return icon to tint
}
