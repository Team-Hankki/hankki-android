package com.hankki.data.storedetail.datasourceImpl

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.storedetail.datasource.StoreDetailDataSource
import com.hankki.data.storedetail.request.MenuUpdateRequestDto
import com.hankki.data.storedetail.response.FavoritesResponseDto
import com.hankki.data.storedetail.response.StoreDetailHeartResponseDto
import com.hankki.data.storedetail.response.StoreDetailNicknameResponseDto
import com.hankki.data.storedetail.response.StoreDetailResponseDto
import com.hankki.data.storedetail.service.StoreDetailService
import javax.inject.Inject

class StoreDetailDataSourceImpl @Inject constructor(
    private val storeDetailService: StoreDetailService
) : StoreDetailDataSource {
    override suspend fun getStoreDetail(
        id: Long
    ): BaseResponse<StoreDetailResponseDto> =
        storeDetailService.getStoreDetail(id)

    override suspend fun postStoreDetailHearts(
        id: Long
    ): BaseResponse<StoreDetailHeartResponseDto> =
        storeDetailService.postStoreDetailHearts(id)

    override suspend fun deleteStoreDetailHearts(
        id: Long
    ): BaseResponse<StoreDetailHeartResponseDto> =
        storeDetailService.deleteStoreDetailHearts(id)

    override suspend fun getMyJogbo(storeId: Long): BaseResponse<FavoritesResponseDto> =
        storeDetailService.getMyJogbo(storeId)

    override suspend fun addStoreAtJogbo(favoriteId: Long, storeId: Long): CreatedBaseResponse =
        storeDetailService.addStoreAtJogbo(favoriteId, storeId)

    override suspend fun getStoreDetailNickname(): BaseResponse<StoreDetailNicknameResponseDto> =
        storeDetailService.getStoreDetailNickname()

    override suspend fun deleteStoreDetail(storeId: Long) =
        storeDetailService.deleteStoreDetail(storeId)

    override suspend fun patchMenuUpdate(storeId: Long, menuId: Long, request: MenuUpdateRequestDto): CreatedBaseResponse =
        storeDetailService.patchMenuUpdate(storeId, menuId, request)
}
