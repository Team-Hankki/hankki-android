package com.hankki.data.storedetail.di

import com.hankki.data.storedetail.datasource.StoreDetailDataSource
import com.hankki.data.storedetail.datasourceImpl.StoreDetailDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindStoreDetailDataSource(
        storeDetailDataSourceImpl: StoreDetailDataSourceImpl
    ): StoreDetailDataSource
}
