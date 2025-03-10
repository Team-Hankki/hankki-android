package com.hankki.data.my.datasourceimpl

import com.hankki.core.network.BaseResponse
import com.hankki.core.network.CreatedBaseResponse
import com.hankki.data.my.datasource.MyDataSource
import com.hankki.data.my.dto.request.JogbosRequestDto
import com.hankki.data.my.dto.request.NewJogboDto
import com.hankki.data.my.dto.response.IsJogboOwnerResponseDto
import com.hankki.data.my.dto.response.JogboDetailDto
import com.hankki.data.my.dto.response.LikedStoreResponseDto
import com.hankki.data.my.dto.response.StoreDto
import com.hankki.data.my.dto.response.MyJogboDto
import com.hankki.data.my.dto.response.UserInformationDto
import com.hankki.data.my.service.MyService
import com.hankki.data.my.service.NoTokenMyService
import javax.inject.Inject

class MyDataSourceImpl @Inject constructor(
    private val myService: MyService,
    private val noTokenMyService: NoTokenMyService
) : MyDataSource {
    override suspend fun getUserInformation(): BaseResponse<UserInformationDto> =
        myService.getUserInformation()
    override suspend fun getMyJogboList(): BaseResponse<MyJogboDto> =
        myService.getMyJogboInformation()
    override suspend fun postNewJogbo(body: NewJogboDto): CreatedBaseResponse =
        myService.postNewJogbo(body)
    override suspend fun getJogboDetail(favoriteId:Long): BaseResponse<JogboDetailDto> =
        myService.getJogboDetail(favoriteId)
    override suspend fun getLikedStore(): BaseResponse<StoreDto> =
        myService.getLikedStore()
    override suspend fun getReportedStore(): BaseResponse<StoreDto> =
        myService.getReportedStore()
    override suspend fun patchLogout(): CreatedBaseResponse = myService.patchLogout()
    override suspend fun deleteWithdraw(): CreatedBaseResponse = myService.deleteWithdraw()
    override suspend fun deleteJogboStore(favoriteId:Long,storeId:Long) =
        myService.deleteJogboStore(favoriteId,storeId)
    override suspend fun deleteJogboStores(body: JogbosRequestDto) =
        myService.deleteJogboStores(body)
    override suspend fun postLikeStore(storeId: Long): BaseResponse<LikedStoreResponseDto> =
        myService.postLikeStore(storeId)
    override suspend fun deleteLikeStore(storeId: Long): BaseResponse<LikedStoreResponseDto> =
        myService.deleteLikeStore(storeId)
    override suspend fun getIsJogboOwner(favoriteId: Long): BaseResponse<IsJogboOwnerResponseDto> =
        myService.getIsJogboOwner(favoriteId)
    override suspend fun postSharedJogbo(favoriteId: Long,body: NewJogboDto): CreatedBaseResponse =
        myService.postSharedJogbo(favoriteId,body)
    override suspend fun getSharedJogboDetail(favoriteId:Long): BaseResponse<JogboDetailDto> =
        noTokenMyService.getSharedJogboDetail(favoriteId)
}
