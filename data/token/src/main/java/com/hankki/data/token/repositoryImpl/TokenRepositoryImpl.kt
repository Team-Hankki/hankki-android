package com.hankki.data.token.repositoryImpl

import com.hankki.core.datastore.TokenDataStore
import com.hankki.domain.token.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore,
): TokenRepository {
    override fun getAccessToken(): String = tokenDataStore.accessToken
    override fun getRefreshToken(): String = tokenDataStore.refreshToken

    override fun setTokens(accessToken:String, refreshToken: String) {
        tokenDataStore.accessToken = accessToken
        tokenDataStore.refreshToken = refreshToken
    }

    override fun clearInfo() {
        tokenDataStore.clearInfo()
    }
}