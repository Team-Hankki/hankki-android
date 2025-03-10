package com.hankki.data.my.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.my.dto.request.JogbosRequestDto
import com.hankki.data.my.dto.request.NewJogboDto
import com.hankki.data.my.dto.response.IsJogboOwnerResponseDto
import com.hankki.data.my.dto.response.JogboDetailDto
import com.hankki.data.my.dto.response.LikedStoreResponseDto
import com.hankki.data.my.dto.response.MyJogboDto
import com.hankki.data.my.dto.response.StoreDto
import com.hankki.data.my.dto.response.UserInformationDto

interface MyDataSource {
    suspend fun getUserInformation(): BaseResponse<UserInformationDto>
    suspend fun getMyJogboList(): BaseResponse<MyJogboDto>
    suspend fun postNewJogbo(body: NewJogboDto): CreatedBaseResponse
    suspend fun getJogboDetail(favoriteId: Long): BaseResponse<JogboDetailDto>
    suspend fun getLikedStore(): BaseResponse<StoreDto>
    suspend fun getReportedStore(): BaseResponse<StoreDto>
    suspend fun patchLogout(): CreatedBaseResponse
    suspend fun deleteWithdraw(): CreatedBaseResponse
    suspend fun deleteJogboStore(favoriteId: Long, storeId: Long)
    suspend fun deleteJogboStores(body: JogbosRequestDto)
    suspend fun postLikeStore(storeId: Long): BaseResponse<LikedStoreResponseDto>
    suspend fun deleteLikeStore(storeId: Long): BaseResponse<LikedStoreResponseDto>
    suspend fun getIsJogboOwner(favoriteId: Long): BaseResponse<IsJogboOwnerResponseDto>
    suspend fun postSharedJogbo(favoriteId: Long, body: NewJogboDto): CreatedBaseResponse
    suspend fun getSharedJogboDetail(favoriteId: Long): BaseResponse<JogboDetailDto>
}
