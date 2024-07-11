package com.hankki.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ReissueOkHttpClient(val isReissue: Boolean = false)