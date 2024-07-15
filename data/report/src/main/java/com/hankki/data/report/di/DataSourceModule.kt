package com.hankki.data.report.di

import com.hankki.data.report.datasource.ReportDataSource
import com.hankki.data.report.datasourceimpl.ReportDataSourceImpl
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
    abstract fun provideReportDataSource(reportDataSourceImpl: ReportDataSourceImpl): ReportDataSource
}
