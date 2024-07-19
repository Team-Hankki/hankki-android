package com.hankki.domain.report.repository

import com.hankki.domain.report.entity.CategoryEntity
import com.hankki.domain.report.entity.CountEntity
import com.hankki.domain.report.entity.LocationEntity
import com.hankki.domain.report.entity.request.ReportStoreRequestEntity
import com.hankki.domain.report.entity.request.ValidateStoreRequestEntity
import com.hankki.domain.report.entity.response.GeneratedStoreResponseEntity
import com.hankki.domain.report.entity.response.JogboResponseEntity
import com.hankki.domain.report.entity.response.UniversityResponseEntity
import com.hankki.domain.report.entity.response.UserInfoResponseEntity

interface ReportRepository {
    suspend fun getStoreLocation(search: String): Result<List<LocationEntity>>
    suspend fun getReportCount(): Result<CountEntity>
    suspend fun getStoreValidate(body: ValidateStoreRequestEntity): Result<Unit>
    suspend fun getCategories(): Result<List<CategoryEntity>>
    suspend fun postReport(
        image: String?,
        request: ReportStoreRequestEntity,
    ): Result<GeneratedStoreResponseEntity>

    suspend fun getMyUniversity(): Result<UniversityResponseEntity>
    suspend fun getUserInfo(): Result<UserInfoResponseEntity>
    suspend fun getFavorites(storeId: Long): Result<List<JogboResponseEntity>>
    suspend fun addStoreAtJogbo(favoriteId: Long, storeId: Long): Result<Unit>
}
