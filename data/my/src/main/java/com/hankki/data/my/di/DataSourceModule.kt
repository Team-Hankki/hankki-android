package com.hankki.data.my.di

import com.hankki.data.my.datasource.MyDataSource
import com.hankki.data.my.datasourceimpl.MyDataSourceImpl
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
    abstract fun bindsMyDataSource(myDataSourceImpl: MyDataSourceImpl): MyDataSource
}

