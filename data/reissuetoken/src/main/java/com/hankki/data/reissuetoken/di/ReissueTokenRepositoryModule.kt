package com.hankki.data.reissuetoken.di

import com.hankki.data.reissuetoken.repositoryImpl.ReissueTokenRepositoryImpl
import com.hankki.domain.reissuetoken.repository.ReissueTokenRepository

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
