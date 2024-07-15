package com.hankki.data.report.di

import com.hankki.data.report.repositoryimpl.ReportRepositoryImpl
import com.hankki.domain.report.repository.ReportRepository
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
    abstract fun getLocations(
        reportRepositoryImpl: ReportRepositoryImpl,
    ): ReportRepository
}
