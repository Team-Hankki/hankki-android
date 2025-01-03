package com.hankki.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoToken

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JWT
