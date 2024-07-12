package com.hankki.data.dummy.di

import com.hankki.core.network.JWT
import com.hankki.data.dummy.service.ReqresService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {
    @Provides
    @Singleton
    fun providePokeService(@JWT retrofit: Retrofit): ReqresService =
        retrofit.create(ReqresService::class.java)
}
