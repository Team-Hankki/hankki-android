package com.hankki.data.login.di

import com.hankki.data.login.datasource.LoginDataSource
import com.hankki.data.login.datasourceImpl.LoginDataSourceImpl
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
    abstract fun provideLoginDataSource(loginDataSourceImpl: LoginDataSourceImpl): LoginDataSource
}
