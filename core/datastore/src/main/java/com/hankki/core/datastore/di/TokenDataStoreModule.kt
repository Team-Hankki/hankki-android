package com.hankki.core.datastore.di

import android.content.SharedPreferences
import com.hankki.core.datastore.TokenDataStore
import com.hankki.core.datastore.TokenDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TokenDataStoreModule {

    @Provides
    @Singleton
    fun provideTokenDataStore(sharedPreferences: SharedPreferences): TokenDataStore {
        return TokenDataStoreImpl(sharedPreferences)
    }
}