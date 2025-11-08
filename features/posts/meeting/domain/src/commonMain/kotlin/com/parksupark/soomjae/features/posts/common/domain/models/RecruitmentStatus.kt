package com.parksupark.soomjae.features.posts.common.domain.models

enum class RecruitmentStatus(val label: String) {
    RECRUITING("모집"),

    // 인원 가득 참
    FULL("만원"),

    // 종료됨
    EXPIRED("종료"),

    // 참여중
    JOINED("참여"),
}
