package com.hankki.data.my.di

import com.hankki.data.my.datasource.MyDataSource
import dagger.Binds
import javax.inject.Singleton

internal abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsMyDataSource(myDataSource: MyDataSource) : MyDataSource
}

