package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.features.posts.common.presentation.models.PostActionType
import com.parksupark.soomjae.features.posts.common.presentation.models.PostActionUi
import com.parksupark.soomjae.features.posts.common.presentation.models.resolveVisuals

@Composable
fun PostActionItem(
    action: PostActionUi,
    modifier: Modifier = Modifier,
) {
    val (icon: ImageVector, tint: Color) = action.type.resolveVisuals(action.isSelected)

    Row(
        modifier = modifier
            .clickable(enabled = action.isEnabled && action.onClick != null) {
                action.onClick?.invoke()
            },
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (action.type is PostActionType.Custom) {
                action.type.description
            } else {
                action.type::class.simpleName
            },
            tint = tint,
            modifier = Modifier.size(24.dp),
        )

        if (action.count != null) {
            Text(
                text = action.count.toString(),
                color = SoomjaeTheme.colorScheme.text2,
                style = SoomjaeTheme.typography.captionM,
            )
        }
    }
}
