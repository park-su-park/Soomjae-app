package com.parksupark.soomjae.features.profile.data.di

import com.parksupark.soomjae.features.profile.data.repository.DefaultIntroductionPostRepository
import com.parksupark.soomjae.features.profile.data.repository.DefaultProfileMemberPostRepository
import com.parksupark.soomjae.features.profile.data.repository.DefaultProfileRepository
import com.parksupark.soomjae.features.profile.data.source.cache.ProfileCacheDataSource
import com.parksupark.soomjae.features.profile.data.source.remote.IntroductionPostRemoteDataSource
import com.parksupark.soomjae.features.profile.data.source.remote.ProfileRemoteDataSource
import com.parksupark.soomjae.features.profile.domain.repository.IntroductionPostRepository
import com.parksupark.soomjae.features.profile.domain.repository.ProfileMemberPostRepository
import com.parksupark.soomjae.features.profile.domain.repository.ProfileRepository
import io.ktor.client.HttpClient
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ksp.generated.module

@Module
internal object ProfileModule {

    @Single
    fun provideProfileCacheDataSource(): ProfileCacheDataSource = ProfileCacheDataSource()

    @Single
    fun provideProfileRemoteDataSource(
        @Provided httpClient: HttpClient,
    ): ProfileRemoteDataSource = ProfileRemoteDataSource(httpClient = httpClient)

    @Single
    fun provideProfileRepository(
        cacheDataSource: ProfileCacheDataSource,
        remoteSource: ProfileRemoteDataSource,
    ): ProfileRepository = DefaultProfileRepository(
        cacheSource = cacheDataSource,
        remoteSource = remoteSource,
    )
}

@Module
internal object IntroductionPostModule {
    @Single
    fun provideIntroductionPostRemoteDataSource(
        @Provided httpClient: HttpClient,
    ): IntroductionPostRemoteDataSource = IntroductionPostRemoteDataSource(
        httpClient = httpClient,
    )

    @Single
    fun provideIntroductionPostRepository(
        remoteSource: IntroductionPostRemoteDataSource,
    ): IntroductionPostRepository = DefaultIntroductionPostRepository(
        remoteSource = remoteSource,
    )
}

private val profileMemberPostModule = module {
    singleOf(::DefaultProfileMemberPostRepository).bind<ProfileMemberPostRepository>()
}

val featuresProfileDataModule = module {
    includes(profileMemberPostModule, ProfileModule.module, IntroductionPostModule.module)
}
