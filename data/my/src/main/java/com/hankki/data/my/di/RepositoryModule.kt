package com.hankki.data.my.di

import com.hankki.domain.my.entity.MyRepository
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
    abstract fun provideMyRepository(
        myRepository: MyRepository
    ) : MyRepository
}
