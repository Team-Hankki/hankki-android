package com.hankki.data.dummy.di

import com.hankki.data.dummy.service.ReqresService
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
    fun providePokeService(retrofit: Retrofit): ReqresService = retrofit.create(ReqresService::class.java)
}
