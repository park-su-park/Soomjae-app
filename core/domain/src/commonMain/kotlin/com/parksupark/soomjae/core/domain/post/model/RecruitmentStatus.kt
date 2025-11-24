package com.parksupark.soomjae.core.domain.post.model

enum class RecruitmentStatus(val label: String) {
    RECRUITING("모집"),

    // 인원 가득 참
    FULL("만원"),

    // 종료됨
    EXPIRED("종료"),

    // 참여중
    JOINED("참여"),
}
