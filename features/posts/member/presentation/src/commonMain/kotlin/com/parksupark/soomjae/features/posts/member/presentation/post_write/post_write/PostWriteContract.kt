package com.parksupark.soomjae.features.posts.member.presentation.post_write.post_write

import com.parksupark.soomjae.features.posts.member.presentation.post_write.MemberPostWriteScreenType

data class PostWriteState(
    val currentScreen: MemberPostWriteScreenType = MemberPostWriteScreenType.PHOTO_PICKER,
)
