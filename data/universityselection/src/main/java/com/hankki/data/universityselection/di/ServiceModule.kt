package com.hankki.data.universityselection.di

import com.hankki.core.network.JWT
import com.hankki.data.universityselection.service.UniversitySelectionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesUniversitySelectionService(@JWT retrofit: Retrofit): UniversitySelectionService =
        retrofit.create(UniversitySelectionService::class.java)
}
