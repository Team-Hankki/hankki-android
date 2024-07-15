package com.hankki.data.home.di

import com.hankki.core.network.JWT
import com.hankki.data.home.service.HomeService
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
    fun provideLoginService(@JWT retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)
}
