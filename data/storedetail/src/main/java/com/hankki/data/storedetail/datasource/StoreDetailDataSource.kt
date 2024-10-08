package com.hankki.data.storedetail.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.storedetail.request.MenuUpdateRequestDto
import com.hankki.data.storedetail.response.FavoritesResponseDto
import com.hankki.data.storedetail.response.StoreDetailHeartResponseDto
import com.hankki.data.storedetail.response.StoreDetailNicknameResponseDto
import com.hankki.data.storedetail.response.StoreDetailResponseDto

interface StoreDetailDataSource {
    suspend fun getStoreDetail(
        id: Long,
    ): BaseResponse<StoreDetailResponseDto>

    suspend fun postStoreDetailHearts(
        id: Long,
    ): BaseResponse<StoreDetailHeartResponseDto>

    suspend fun deleteStoreDetailHearts(
        id: Long,
    ): BaseResponse<StoreDetailHeartResponseDto>

    suspend fun getMyJogbo(storeId: Long): BaseResponse<FavoritesResponseDto>

    suspend fun addStoreAtJogbo(favoriteId: Long, storeId: Long): CreatedBaseResponse

    suspend fun getStoreDetailNickname(): BaseResponse<StoreDetailNicknameResponseDto>

    suspend fun deleteStoreDetail(storeId: Long)

    suspend fun patchMenuUpdate(storeId: Long, menuId: Long, request: MenuUpdateRequestDto): CreatedBaseResponse
}
