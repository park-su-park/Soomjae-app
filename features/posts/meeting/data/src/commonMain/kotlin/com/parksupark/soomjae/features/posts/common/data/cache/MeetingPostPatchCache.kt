package com.parksupark.soomjae.features.posts.common.data.cache

import com.parksupark.soomjae.core.data.cache.ReactiveCache
import com.parksupark.soomjae.core.domain.post.model.MeetingPost
import com.parksupark.soomjae.core.domain.post.model.MeetingPostPatch

internal class MeetingPostPatchCache(
    private val delegate: ReactiveCache<Long, MeetingPostPatch> = ReactiveCache(),
) {
    val modifiedPostsStream = delegate.updates

    fun applyLike(
        postId: Long,
        liked: Boolean,
    ) {
        delegate.update(
            postId,
            MeetingPostPatch.LikeChanged(liked),
        )
    }

    fun applyUpdate(post: MeetingPost) {
        delegate.update(
            post.id,
            MeetingPostPatch.Updated(post = post),
        )
    }

    fun applyDelete(postId: Long) {
        delegate.update(
            postId,
            MeetingPostPatch.Deleted,
        )
    }

    fun removePatch(postId: Long) {
        delegate.remove(postId)
    }

    fun clear() {
        delegate.clear()
    }
}
