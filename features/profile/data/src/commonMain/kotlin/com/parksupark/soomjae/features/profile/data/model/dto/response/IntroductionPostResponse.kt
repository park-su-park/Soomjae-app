package com.parksupark.soomjae.features.profile.data.model.dto.response

import com.parksupark.soomjae.features.profile.domain.model.IntroductionPost
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.toStdlibInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class IntroductionPostResponse(
    @SerialName("author") val author: AuthorResponse,
    @SerialName("postId") val postId: Int,
    @SerialName("content") val htmlContent: String,
    @SerialName("createdTime") val createdTime: Instant,
)

@OptIn(ExperimentalTime::class)
internal fun IntroductionPostResponse.toIntroductionPost(): IntroductionPost = IntroductionPost(
    authorId = author.memberId,
    htmlContent = htmlContent,
    lastModifiedAt = createdTime.toStdlibInstant(),
)
