package com.hankki.feature.storedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hankki.feature.storedetail.model.MenuItem
import com.hankki.feature.storedetail.model.StoreDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoreDetailViewModel : ViewModel() {
    private val _storeDetail = MutableStateFlow<StoreDetail?>(null)
    val storeDetail: StateFlow<StoreDetail?> = _storeDetail.asStateFlow()

    private var mockStoreDetail = StoreDetail(
        name = "한끼네 한정식",
        category = "한식",
        isLiked = true,
        heartCount = 300,
        imageUrls = listOf(
            "www.example.com/image1",
            "www.example.com/image2",
            "www.example.com/image3"
        ),
        menus = listOf(
            MenuItem(name = "수육정식", price = 7900),
            MenuItem(name = "제육정식", price = 8900),
            MenuItem(name = "꼬막정식", price = 7900)
        )
    )

    init {
        fetchStoreDetail()
    }

    private fun fetchStoreDetail() {
        viewModelScope.launch {
            _storeDetail.value = mockStoreDetail
        }
    }

    fun toggleLike() {
        viewModelScope.launch {
            val newHeartCount =
                if (mockStoreDetail.isLiked) mockStoreDetail.heartCount - 1 else mockStoreDetail.heartCount + 1
            mockStoreDetail =
                mockStoreDetail.copy(isLiked = !mockStoreDetail.isLiked, heartCount = newHeartCount)

            _storeDetail.value = mockStoreDetail
        }
    }
}
