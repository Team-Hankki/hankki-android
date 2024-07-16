package com.hankki.data.report.di

import com.hankki.core.network.JWT
import com.hankki.data.report.service.ReportService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideReportService(@JWT retrofit: Retrofit): ReportService =
        retrofit.create(ReportService::class.java)
}
