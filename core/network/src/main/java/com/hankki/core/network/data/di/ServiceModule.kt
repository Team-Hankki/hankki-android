package com.hankki.core.network.data.di

import com.hankki.core.network.Reissue
import com.hankki.core.network.data.service.ReissueTokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideReissueTokenService(@Reissue retrofit: Retrofit): ReissueTokenService =
        retrofit.create(ReissueTokenService::class.java)
}
