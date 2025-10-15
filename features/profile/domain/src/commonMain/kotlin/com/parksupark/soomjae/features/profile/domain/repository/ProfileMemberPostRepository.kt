package com.parksupark.soomjae.features.profile.domain.repository

import androidx.paging.PagingData
import com.parksupark.soomjae.features.profile.domain.models.ProfileMemberPost
import kotlinx.coroutines.flow.Flow

interface ProfileMemberPostRepository {
    fun getPagedPosts(memberId: Long): Flow<PagingData<ProfileMemberPost>>
}
