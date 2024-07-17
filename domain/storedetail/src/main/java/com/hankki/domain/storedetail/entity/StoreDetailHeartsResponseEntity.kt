package com.hankki.domain.storedetail.entity

data class StoreDetailHeartsResponseEntity(
    val storeId: Long,
    val count: Int,
    var isHearted: Boolean,
)
