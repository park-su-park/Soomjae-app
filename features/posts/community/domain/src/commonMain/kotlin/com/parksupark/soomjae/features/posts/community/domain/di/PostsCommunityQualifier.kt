package com.parksupark.soomjae.features.posts.community.domain.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.named

object PostsCommunityQualifier {
    val LIKE_REPOSITORY: Qualifier = named("community_post_like_repository")
    val COMMENT_REPOSITORY: Qualifier = named("community_post_comment_repository")
}
