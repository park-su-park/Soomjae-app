package com.parksupark.soomjae.core.presentation.ui.post.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme

@Composable
fun MeetingPostCardChip(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = SoomjaeTheme.colorScheme.background3,
    contentColor: Color = SoomjaeTheme.colorScheme.text3,
) {
    SoomjaeSurface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = SoomjaeTheme.typography.labelM,
        )
    }
}
