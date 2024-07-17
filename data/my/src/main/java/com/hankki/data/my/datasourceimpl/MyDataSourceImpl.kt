package com.hankki.data.my.datasourceimpl

import com.hankki.core.network.BaseResponse
import com.hankki.data.my.datasource.MyDataSource
import com.hankki.data.my.dto.response.UserInformationDto
import com.hankki.data.my.service.MyService
import javax.inject.Inject

class MyDataSourceImpl @Inject constructor(
    private val myService: MyService
) : MyDataSource {
    override suspend fun getUserInformation(): BaseResponse<UserInformationDto> =
        myService.getUserInformation()
}
