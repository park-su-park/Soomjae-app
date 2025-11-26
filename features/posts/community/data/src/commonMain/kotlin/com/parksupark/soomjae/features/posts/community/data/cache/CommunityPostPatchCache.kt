package com.parksupark.soomjae.features.posts.community.data.cache

import com.parksupark.soomjae.core.data.cache.ReactiveCache
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPost
import com.parksupark.soomjae.features.posts.community.domain.model.CommunityPostPatch

class CommunityPostPatchCache(
    private val delegate: ReactiveCache<Long, CommunityPostPatch> = ReactiveCache(),
) {
    val modifiedPostsStream = delegate.updates

    fun applyLike(
        postId: Long,
        isLiked: Boolean,
    ) {
        delegate.update(
            postId,
            CommunityPostPatch.LikeChanged(isLiked),
        )
    }

    fun applyUpdate(post: CommunityPost) {
        delegate.update(
            post.id,
            CommunityPostPatch.Updated(post = post),
        )
    }

    fun applyDelete(postId: Long) {
        delegate.update(
            postId,
            CommunityPostPatch.Deleted,
        )
    }

    fun removePatch(postId: Long) {
        delegate.remove(postId)
    }

    fun clear() {
        delegate.clear()
    }
}
