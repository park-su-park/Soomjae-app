package com.parksupark.soomjae.features.posts.common.domain.models

data class MeetingForEdit(
    val id: Long,
    val title: String,
    val content: String,
    val category: Category?,
    val location: Location?,
    val recruitment: MeetingRecruitment,
)
