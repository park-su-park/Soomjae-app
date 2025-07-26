package com.parksupark.soomjae.features.posts.common.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.ui.utils.imageRequest
import com.parksupark.soomjae.features.posts.common.presentation.models.CommentUi

@Composable
fun CommentItem(
    comment: CommentUi,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = imageRequest { data(comment.author.profileImageUrl) },
            contentDescription = null,
            modifier = Modifier.size(40.dp),
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(text = comment.author.nickname, style = SoomjaeTheme.typography.labelM)

            Text(text = comment.content, style = SoomjaeTheme.typography.body1)

            Text(
                text = comment.formattedCreatedAt,
                style = SoomjaeTheme.typography.captionM.copy(
                    color = SoomjaeTheme.colorScheme.text3,
                ),
            )
        }
    }
}
