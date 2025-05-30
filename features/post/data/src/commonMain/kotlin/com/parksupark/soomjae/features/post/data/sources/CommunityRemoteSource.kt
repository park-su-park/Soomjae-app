package com.parksupark.soomjae.features.post.data.sources

import arrow.core.Either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.post.data.dtos.CommunityPostResponse
import kotlinx.collections.immutable.ImmutableList

internal interface CommunityRemoteSource {
    suspend fun getPosts(page: Int): Either<DataFailure.Network, ImmutableList<CommunityPostResponse>>
}
