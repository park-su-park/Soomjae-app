package com.parksupark.soomjae.features.posts.common.domain.models

import com.parksupark.soomjae.core.domain.post.model.Category
import com.parksupark.soomjae.core.domain.post.model.Location

data class MeetingForEdit(
    val id: Long,
    val title: String,
    val content: String,
    val category: Category?,
    val location: Location?,
    val recruitment: MeetingRecruitment,
)
