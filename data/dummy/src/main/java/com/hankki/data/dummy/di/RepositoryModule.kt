package com.hankki.data.dummy.di

import com.hankki.data.dummy.repositoryimpl.ReqresRepositoryImpl
import com.hankki.domain.dummy.repository.ReqresRepository
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
    abstract fun bindRequresRepository(reqresRepositoryImpl: ReqresRepositoryImpl): ReqresRepository
}
