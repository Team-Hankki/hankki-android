package com.hankki.data.home.di

import com.hankki.data.home.repositoryImpl.HomeRepositoryImpl
import com.hankki.domain.home.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl,
    ): HomeRepository
}
