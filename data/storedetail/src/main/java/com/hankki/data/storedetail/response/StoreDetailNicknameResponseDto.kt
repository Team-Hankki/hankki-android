package com.hankki.data.storedetail.response

import com.hankki.domain.storedetail.entity.StoreDetailNicknameEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoreDetailNicknameResponseDto(
    @SerialName("nickname")
    val nickname: String
) {
    fun toStoreDetailNicknameEntity() = StoreDetailNicknameEntity(
        nickname = nickname
    )
}