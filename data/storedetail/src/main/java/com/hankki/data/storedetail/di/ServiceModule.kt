package com.hankki.data.storedetail.di

import com.hankki.core.network.JWT
import com.hankki.data.storedetail.service.StoreDetailService
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
    fun provideStoreDetailService(@JWT retrofit: Retrofit): StoreDetailService =
        retrofit.create(StoreDetailService::class.java)
}
