package com.hankki.core.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.hankki.core.datastore.TokenDataStore
import com.hankki.domain.reissuetoken.repository.ReissueTokenRepository
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
            runBlocking {
                handleTokenExpiration(chain, authRequest, response)
            }
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

    private suspend fun handleTokenExpiration(
        chain: Interceptor.Chain,
        authRequest: Request,
        response: Response
    ): Response {
        response.close()
        return if (tryReissueToken()) {
            val newRequest =
                authRequest.newBuilder().addAuthorizationHeader().build()
            chain.proceed(newRequest)
        } else {
            clearUserInfoAndRestart()
            chain.proceed(authRequest.newBuilder().build())
        }
    }

    private suspend fun tryReissueToken(): Boolean {
        return try {
            val result =
                reissueTokenRepository.postReissueToken("$BEARER ${dataStore.refreshToken}")
            result.onSuccess { data ->
                Timber.d("Successfully reissued token: ${data.refreshToken}")
                updateTokens(data.accessToken, data.refreshToken)
            }.onFailure { error ->
                Timber.e("Failed to reissue token: $error")
            }
            result.isSuccess
        } catch (t: Throwable) {
            Timber.e(t, "Exception occurred while reissuing token")
            false
        }
    }

    private fun updateTokens(newAccessToken: String, newRefreshToken: String) {
        Timber.e("NEW ACCESS TOKEN : $newAccessToken")
        Timber.e("NEW REFRESH TOKEN : $newRefreshToken")
        dataStore.apply {
            accessToken = newAccessToken
            refreshToken = newRefreshToken
        }
    }

    private fun clearUserInfoAndRestart() {
        dataStore.clearInfo()
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, R.string.relogin_message, Toast.LENGTH_LONG).show()
            ProcessPhoenix.triggerRebirth(context)
        }
    }

    companion object {
        private const val TOKEN_EXPIRED = 401
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }
}
