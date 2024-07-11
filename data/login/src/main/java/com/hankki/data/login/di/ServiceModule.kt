package com.hankki.data.login.di

import com.hankki.core.network.Reissue
import com.hankki.data.login.service.LoginService
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
    fun provideLoginService(@Reissue retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)
}
