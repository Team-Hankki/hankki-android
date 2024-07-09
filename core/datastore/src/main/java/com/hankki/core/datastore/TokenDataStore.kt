package com.hankki.core.datastore

interface TokenDataStore {
    var accessToken: String
    var refreshToken: String
    fun clearInfo()
}