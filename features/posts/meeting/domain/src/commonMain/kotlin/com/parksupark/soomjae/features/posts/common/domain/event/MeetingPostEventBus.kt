package com.parksupark.soomjae.features.posts.common.domain.event

import com.parksupark.soomjae.features.posts.common.domain.models.MeetingPost
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class MeetingPostEventBus {
    private val _created = MutableSharedFlow<MeetingPost>()
    val created: SharedFlow<MeetingPost> = _created

    suspend fun emitCreated(post: MeetingPost) {
        _created.emit(post)
    }
}
