package com.parksupark.soomjae.features.posts.community.domain.usecases

import arrow.core.Either
import arrow.core.raise.either
import com.parksupark.soomjae.core.domain.failures.DataFailure
import com.parksupark.soomjae.features.posts.community.domain.models.CommunityPostDetailWithLiked
import com.parksupark.soomjae.features.posts.community.domain.repositories.CommunityPostLikeRepository
import com.parksupark.soomjae.features.posts.community.domain.repositories.CommunityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetCommunityPostDetailWithLikedStream(
    private val postRepository: CommunityRepository,
    private val likeRepository: CommunityPostLikeRepository,
) {

    operator fun invoke(postId: Long): Flow<Either<DataFailure, CommunityPostDetailWithLiked>> = combine(
        postRepository.postDetailStream(postId),
        likeRepository.likedStream(postId),
    ) { postDetail, liked ->
        either {
            val post = postDetail.bind()
            val like = liked.bind()

            CommunityPostDetailWithLiked(
                post = post,
                isLiked = like.liked,
            )
        }
    }
}
