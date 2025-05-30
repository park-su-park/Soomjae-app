package com.parksupark.soomjae.features.post.presentation.post.models

import com.parksupark.soomjae.features.post.domain.models.CommunityPost

internal data class CommunityPostUi(
    val id: String,
    val title: String,
    val contentPreview: String,
    val author: AuthorUi,
    val createdAt: String,
)

// TODO: Add more fields as needed
internal fun CommunityPost.toUi(): CommunityPostUi = CommunityPostUi(
    id = id,
    title = "건강한 라이프스타일을 위한 스트레스 관리 방법",
    contentPreview = "건강과 지속 가능성을 추구하는 이들을 위해, 맛과 영양이 가득한 채식 요리 레시피를 소개합니다. 이 글에서는 간단하지만 맛있는 채식 요리",
    author = AuthorUi(
        id = "1234",
        name = "silentfox11",
        profileImageUrl = "https://picsum.photos/200",
    ),
    createdAt = "1 hour ago",
)
