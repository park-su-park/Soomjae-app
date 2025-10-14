package com.parksupark.soomjae.features.posts.meeting.presentation.tab.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parksupark.soomjae.core.presentation.designsystem.components.SoomjaeSurface
import com.parksupark.soomjae.core.presentation.designsystem.theme.AppTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.SoomjaeTheme
import com.parksupark.soomjae.core.presentation.designsystem.theme.infoContainer
import com.parksupark.soomjae.core.presentation.designsystem.theme.onInfoContainer
import com.parksupark.soomjae.core.presentation.designsystem.theme.onSuccessContainer
import com.parksupark.soomjae.core.presentation.designsystem.theme.successContainer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun RecruitmentStatusChip(
    status: RecruitmentStatus,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, contentColor) = when (status) {
        RecruitmentStatus.RECRUITING -> SoomjaeTheme.colorScheme.successContainer to SoomjaeTheme.colorScheme.onSuccessContainer
        RecruitmentStatus.JOINED -> SoomjaeTheme.colorScheme.infoContainer to SoomjaeTheme.colorScheme.onInfoContainer
        else -> SoomjaeTheme.colorScheme.background3 to SoomjaeTheme.colorScheme.text3
    }

    MeetingPostCardChip(
        text = status.label,
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    )
}

enum class RecruitmentStatus(val label: String) {
    RECRUITING("모집"),

    // 인원 가득 참
    FULL("만원"),

    // 종료됨
    EXPIRED("종료"),

    // 참여중
    JOINED("참여"),
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentStatusChipPreview() {
    AppTheme {
        SoomjaeSurface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                RecruitmentStatus.entries.forEach {
                    RecruitmentStatusChip(status = it)
                }
            }
        }
    }
}
