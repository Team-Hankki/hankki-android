package com.hankki.feature.main

import androidx.lifecycle.ViewModel
import com.hankki.feature.main.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    networkMonitor: NetworkMonitor
) : ViewModel() {
    val isConnected = networkMonitor.isConnected
}