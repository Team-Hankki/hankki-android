package com.hankki.data.reissuetoken.di

import com.hankki.data.reissuetoken.datasource.ReissueTokenDataSource
import com.hankki.data.reissuetoken.datasourceImpl.ReissueTokenDataSourceImpl
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
    abstract fun bindReissueTokenDataSource(
        reissueTokenDataSourceImpl: ReissueTokenDataSourceImpl
    ): ReissueTokenDataSource
}
