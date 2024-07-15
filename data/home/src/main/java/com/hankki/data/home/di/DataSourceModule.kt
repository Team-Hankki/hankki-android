package com.hankki.data.home.di

import com.hankki.data.home.datasource.HomeDataSource
import com.hankki.data.home.datasourcimpl.HomeDataSourceImpl
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
    abstract fun provideHomeDataSource(homeDataSourceImpl: HomeDataSourceImpl): HomeDataSource
}
