package com.hankki.data.my.di

import com.hankki.core.network.JWT
import com.hankki.data.my.service.MyService
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
    fun providesMyDataSource(@JWT retrofit: Retrofit): MyService =
        retrofit.create(MyService::class.java)
}
