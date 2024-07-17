package com.hankki.data.universityselection.dto

import com.hankki.data.universityselection.datasource.UniversitySelectionDataSource
import com.hankki.data.universityselection.datasourceImpl.UniversirySelectionDataSourceImpl
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
    abstract fun bindsUniversitySelectionDataSource(
        universirySelectionDataSourceImpl: UniversirySelectionDataSourceImpl
    ): UniversitySelectionDataSource
}
