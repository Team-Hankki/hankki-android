package com.hankki.domain.my.entity.response

data class LikedStoreResponseEntity(
    val storeId: Long,
    val count: Int,
    val isHearted: Boolean
)
