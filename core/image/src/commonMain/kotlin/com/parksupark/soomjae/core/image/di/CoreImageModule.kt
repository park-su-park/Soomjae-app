package com.parksupark.soomjae.core.image.di

import com.parksupark.soomjae.core.image.data.datasources.NetworkUploader
import com.parksupark.soomjae.core.image.data.processor.DefaultImageProcessor
import com.parksupark.soomjae.core.image.data.repositories.DefaultImageRepository
import com.parksupark.soomjae.core.image.domain.ImageProcessor
import com.parksupark.soomjae.core.image.domain.ImageRepository
import com.parksupark.soomjae.core.image.domain.usecase.UploadImageWithProgressUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ksp.generated.module

private val coreDataModule = module {
    singleOf(::NetworkUploader)
    singleOf(::DefaultImageRepository).bind<ImageRepository>()

    singleOf(::DefaultImageProcessor).bind<ImageProcessor>()
}

@Module
internal object CoreDomainModule {
    @Factory
    fun createUploadImageWithProgressUseCase(
        @Provided imageRepository: ImageRepository,
    ): UploadImageWithProgressUseCase = UploadImageWithProgressUseCase(
        imageRepository = imageRepository,
    )
}

val coreImageModule = module {
    includes(coreDataModule, CoreDomainModule.module)
}
