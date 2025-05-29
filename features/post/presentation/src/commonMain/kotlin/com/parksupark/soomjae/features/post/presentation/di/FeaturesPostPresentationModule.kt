package com.parksupark.soomjae.features.post.presentation.di

import com.parksupark.soomjae.features.post.presentation.post.PostViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val postModule = module {
    viewModelOf(::PostViewModel)
}

val featuresPostPresentationModule = module {
    includes(postModule)
}
