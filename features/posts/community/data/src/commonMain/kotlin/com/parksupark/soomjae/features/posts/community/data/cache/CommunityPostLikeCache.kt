package com.parksupark.soomjae.features.posts.community.data.cache

import com.parksupark.soomjae.core.data.cache.ReactiveCache
import kotlinx.coroutines.flow.StateFlow

internal class CommunityPostLikeCache(
    private val delegate: ReactiveCache<Long, Boolean> = ReactiveCache(),
) {
    val likeStates: StateFlow<Map<Long, Boolean>> get() = delegate.updates

    fun updateLike(
        postId: Long,
        liked: Boolean,
    ) {
        delegate.update(postId, liked)
    }

    fun isLiked(postId: Long): Boolean? = delegate[postId]

    fun clear() {
        delegate.clear()
    }
}
