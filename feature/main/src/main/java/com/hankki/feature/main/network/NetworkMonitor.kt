package com.hankki.feature.main.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitor {
    val isConnected: StateFlow<Boolean>
    fun unregisterCallback()
}
