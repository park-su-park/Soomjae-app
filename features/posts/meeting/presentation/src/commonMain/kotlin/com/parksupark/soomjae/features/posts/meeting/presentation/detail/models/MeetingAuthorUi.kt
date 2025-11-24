package com.parksupark.soomjae.features.posts.meeting.presentation.detail.models

import com.parksupark.soomjae.core.presentation.ui.post.model.AuthorUi

internal data class MeetingAuthorUi(
    val author: AuthorUi,
    val review: MeetingReviewUi?,
)

data class MeetingReviewUi(
    val rating: Float,
    val reviewCount: Int,
)
