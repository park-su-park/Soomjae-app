package com.parksupark.soomjae.features.profile.data.source.cache

import com.parksupark.soomjae.core.data.cache.ReactiveCache
import com.parksupark.soomjae.features.profile.domain.model.Profile

internal class ProfileCacheDataSource(
    private val delegate: ReactiveCache<Long, Profile> = ReactiveCache(),
) {
    val profiles = delegate.updates

    fun upsertProfile(profile: Profile) {
        delegate.update(
            profile.memberId,
            profile,
        )
    }

    fun getProfile(memberId: Long): Profile? {
        return delegate[memberId]
    }
}
