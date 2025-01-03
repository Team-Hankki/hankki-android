package com.hankki.data.my.di

import com.hankki.core.network.JWT
import com.hankki.core.network.NoToken
import com.hankki.data.my.service.MyService
import com.hankki.data.my.service.NoTokenMyService
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
    fun providesMyService(@JWT retrofit: Retrofit): MyService =
        retrofit.create(MyService::class.java)

    @Provides
    @Singleton
    fun provideNoTokenMyService(@NoToken retrofit: Retrofit): NoTokenMyService =
        retrofit.create(NoTokenMyService::class.java)
}
