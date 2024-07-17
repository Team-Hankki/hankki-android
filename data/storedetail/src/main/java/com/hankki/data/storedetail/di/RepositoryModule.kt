package com.hankki.data.storedetail.di

import com.hankki.data.storedetail.repositoryImpl.StoreDetailRepositoryImpl
import com.hankki.domain.storedetail.repository.StoreDetailRepository
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
    abstract fun bindStoreDetailRepository(
        storeDetailRepositoryImpl: StoreDetailRepositoryImpl
       ) : StoreDetailRepository
}
