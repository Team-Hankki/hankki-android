package com.hankki.core.network

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.hankki.core.datastore.TokenDataStore
import com.hankki.core.network.domain.repository.ReissueTokenRepository
import com.hankki.feature.main.MainActivity
import com.jakewharton.processphoenix.ProcessPhoenix
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class OauthInterceptor @Inject constructor(
    private val reissueTokenRepository: ReissueTokenRepository,
    private val dataStore: TokenDataStore,
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest = addAuthorization(originalRequest)

        val response = chain.proceed(authRequest)
        return if (response.code == TOKEN_EXPIRED) {
            handleTokenExpiration(chain, authRequest, response)
        } else {
            response
        }
    }

    private fun addAuthorization(request: Request): Request {
        return if (dataStore.accessToken.isNotBlank()) {
            request.newBuilder().addAuthorizationHeader().build()
        } else {
            request
        }
    }

    private fun Request.Builder.addAuthorizationHeader() =
        this.addHeader(AUTHORIZATION, "$BEARER ${dataStore.accessToken}")

    private fun handleTokenExpiration(
        chain: Interceptor.Chain,
        authRequest: Request,
        response: Response
    ): Response {
        response.close()
        return if (tryReissueToken()) {
            val newRequest = authRequest.newBuilder().addAuthorizationHeader().build()
            chain.proceed(newRequest)
        } else {
            clearUserInfoAndRestart()
            response
        }
    }

    private fun tryReissueToken(): Boolean {
        return try {
            runBlocking {
                reissueTokenRepository.postReissueToken(dataStore.refreshToken).onSuccess { data ->
                    updateTokens(data.accessToken, data.refreshToken)
                }
            }
            true
        } catch (t: Throwable) {
            Timber.d(t.message)
            false
        }
    }

    private fun updateTokens(newAccessToken: String, newRefreshToken: String) {
        Timber.d("NEW ACCESS TOKEN : $newAccessToken")
        Timber.d("NEW REFRESH TOKEN : $newRefreshToken")
        dataStore.apply {
            accessToken = newAccessToken
            refreshToken = newRefreshToken
        }
    }

    private fun clearUserInfoAndRestart() {
        dataStore.clearInfo()
        Handler(Looper.getMainLooper()).post {
            ProcessPhoenix.triggerRebirth(context, Intent(context, MainActivity::class.java))
        }
    }

    companion object {
        private const val TOKEN_EXPIRED = 401
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }
}
