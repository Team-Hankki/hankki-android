package com.hankki.core.network.data.di

import com.hankki.core.network.data.repositoryImpl.ReissueTokenRepositoryImpl
import com.hankki.core.network.domain.repository.ReissueTokenRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ReissueTokenRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindReissueTokenRepository(
        reissueTokenRepositoryImpl: ReissueTokenRepositoryImpl
    ): ReissueTokenRepository
}
