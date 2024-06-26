package com.hankki.data.dummy.di

import com.hankki.data.dummy.datasource.ReqresDataSource
import com.hankki.data.dummy.datasourceimpl.ReqresDataSourceImpl
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
    abstract fun bindRequresDataSource(impl: ReqresDataSourceImpl): ReqresDataSource
}
