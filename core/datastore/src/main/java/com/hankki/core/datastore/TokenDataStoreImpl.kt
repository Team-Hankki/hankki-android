package com.hankki.core.datastore

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class TokenDataStoreImpl @Inject constructor(
    private val tokenDataStore: SharedPreferences,
) : TokenDataStore {
    override var accessToken: String
        get() = tokenDataStore.getString(ACCESS_TOKEN, "") ?: ""
        set(value) = tokenDataStore.edit { putString(ACCESS_TOKEN, value)}

    override var refreshToken: String
        get() = tokenDataStore.getString(REFRESH_TOKEN, "") ?: ""
        set(value) = tokenDataStore.edit { putString(REFRESH_TOKEN, value) }

    override fun clearInfo() {
        tokenDataStore.edit().clear().commit()
    }

    companion object {
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }
}