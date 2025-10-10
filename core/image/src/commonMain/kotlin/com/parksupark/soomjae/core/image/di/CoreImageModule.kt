package com.parksupark.soomjae.core.image.di

import com.parksupark.soomjae.core.image.data.datasources.NetworkUploader
import com.parksupark.soomjae.core.image.data.processor.DefaultImageProcessor
import com.parksupark.soomjae.core.image.data.repositories.DefaultImageRepository
import com.parksupark.soomjae.core.image.domain.ImageProcessor
import com.parksupark.soomjae.core.image.domain.ImageRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val coreDataModule = module {
    singleOf(::NetworkUploader)
    singleOf(::DefaultImageRepository).bind<ImageRepository>()

    singleOf(::DefaultImageProcessor).bind<ImageProcessor>()
}

val coreImageModule = module {
    includes(coreDataModule)
}
