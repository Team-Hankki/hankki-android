package com.hankki.data.my.datasource

import com.hankki.core.network.BaseResponse
import com.hankki.data.my.dto.response.UserInformationDto

interface MyDataSource {
    suspend fun getUserInformation(): BaseResponse<UserInformationDto>
}
