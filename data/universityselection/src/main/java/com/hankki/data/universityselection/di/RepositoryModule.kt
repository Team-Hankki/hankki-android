package com.hankki.data.universityselection.di

import com.hankki.data.universityselection.repositoryImpl.UniversitySelectionRepositoryImpl
import com.hankki.domain.universityselection.repository.UniversitySelectionRepository
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
    abstract fun bindsUniversitySelectionRepository(
        universitySelectionRepositoryImpl: UniversitySelectionRepositoryImpl
    ) : UniversitySelectionRepository
}
