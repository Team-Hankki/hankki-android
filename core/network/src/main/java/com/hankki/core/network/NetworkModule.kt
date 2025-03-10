package com.hankki.core.network

import com.hankki.core.network.BuildConfig.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    private const val APPLICATION_JSON = "application/json"
    private const val OKHTTP = "okhttp"
    private const val UNIT_TAB = 4

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory(APPLICATION_JSON.toMediaType())

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor { message ->
        when {
            message.isJsonObject() ->
                Timber.tag(OKHTTP).d(JSONObject(message).toString(UNIT_TAB))

            message.isJsonArray() ->
                Timber.tag(OKHTTP).d(JSONArray(message).toString(UNIT_TAB))

            else -> {
                Timber.tag(OKHTTP).d("CONNECTION INFO -> $message")
            }
        }
    }.apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    @JWT
    fun provideOauthInterceptor(authInterceptor: OauthInterceptor): Interceptor = authInterceptor

    @Provides
    @Singleton
    @JWT
    fun provideJWTOkHttpClient(
        loggingInterceptor: Interceptor,
        @JWT oauthInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(oauthInterceptor)
        .build()

    @Provides
    @Singleton
    @NoToken
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    @JWT
    fun provideJWTRetrofit(
        @JWT client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()

    @Provides
    @Singleton
    @NoToken
    fun provideReissueRetrofit(
        @NoToken client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()
}
