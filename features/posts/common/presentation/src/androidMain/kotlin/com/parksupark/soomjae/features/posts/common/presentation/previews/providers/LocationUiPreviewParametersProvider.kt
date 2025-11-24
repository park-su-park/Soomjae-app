package com.parksupark.soomjae.features.posts.common.presentation.previews.providers

import com.parksupark.soomjae.core.domain.post.model.Location
import kotlinx.collections.immutable.persistentListOf

object LocationPreviewParametersData {
    val locations = persistentListOf(
        Location(
            code = 11L,
            name = "서울특별시",
        ),
        Location(
            code = 26L,
            name = "부산광역시",
        ),
        Location(
            code = 27L,
            name = "대구광역시",
        ),
        Location(
            code = 28L,
            name = "인천광역시",
        ),
        Location(
            code = 29L,
            name = "광주광역시",
        ),
        Location(
            code = 30L,
            name = "대전광역시",
        ),
        Location(
            code = 31L,
            name = "울산광역시",
        ),
        Location(
            code = 36L,
            name = "세종특별자치시",
        ),
        Location(
            code = 41L,
            name = "경기도",
        ),
        Location(
            code = 42L,
            name = "강원도",
        ),
        Location(
            code = 43L,
            name = "충청북도",
        ),
        Location(
            code = 44L,
            name = "충청남도",
        ),
        Location(
            code = 45L,
            name = "전라북도",
        ),
        Location(
            code = 46L,
            name = "전라남도",
        ),
        Location(
            code = 47L,
            name = "경상북도",
        ),
        Location(
            code = 48L,
            name = "경상남도",
        ),
        Location(
            code = 50L,
            name = "제주특별자치도",
        ),
    )
}
