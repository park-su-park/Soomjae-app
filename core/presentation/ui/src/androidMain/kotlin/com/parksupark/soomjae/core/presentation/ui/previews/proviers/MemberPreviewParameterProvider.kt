package com.parksupark.soomjae.core.presentation.ui.previews.proviers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.parksupark.soomjae.core.domain.models.Member
import com.parksupark.soomjae.core.presentation.ui.previews.proviers.MemberPreviewParameterData.members

class MemberPreviewParameterProvider : PreviewParameterProvider<List<Member>> {
    override val values: Sequence<List<Member>> = sequenceOf(members)
}

object MemberPreviewParameterData {
    val members = listOf(
        Member(
            id = "1",
            nickname = "Expert Plumber",
            profileImageUrl = "https://picsum.photos/200/300?random=1",
        ),
        Member(
            id = "2",
            nickname = "Language Tutor",
            profileImageUrl = "https://picsum.photos/200/300?random=2",
        ),
        Member(
            id = "3",
            nickname = "Fitness Coach",
            profileImageUrl = "https://picsum.photos/200/300?random=3",
        ),
        Member(
            id = "4",
            nickname = "Artistic Director",
            profileImageUrl = "https://picsum.photos/200/300?random=4",
        ),
    )
}
