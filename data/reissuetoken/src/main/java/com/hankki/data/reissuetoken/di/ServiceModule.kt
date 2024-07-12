package com.hankki.data.reissuetoken.di

import com.hankki.core.network.REISSUE
import com.hankki.data.reissuetoken.service.ReissueTokenService
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
    fun provideReissueTokenService(@REISSUE retrofit: Retrofit): ReissueTokenService =
        retrofit.create(ReissueTokenService::class.java)
}
