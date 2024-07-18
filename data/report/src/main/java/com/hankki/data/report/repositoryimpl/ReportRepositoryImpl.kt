package com.hankki.data.report.repositoryimpl

import android.content.Context
import android.net.Uri
import com.hankki.core.common.utill.ContentUriRequestBody
import com.hankki.data.report.datasource.ReportDataSource
import com.hankki.data.report.dto.request.toDto
import com.hankki.data.report.dto.response.toEntity
import com.hankki.domain.report.entity.CategoryEntity
import com.hankki.domain.report.entity.CountEntity
import com.hankki.domain.report.entity.LocationEntity
import com.hankki.domain.report.entity.request.ReportStoreRequestEntity
import com.hankki.domain.report.entity.request.ValidateStoreRequestEntity
import com.hankki.domain.report.entity.response.GeneratedStoreResponseEntity
import com.hankki.domain.report.entity.response.UniversityResponseEntity
import com.hankki.domain.report.repository.ReportRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val reportDataSource: ReportDataSource,
) : ReportRepository {
    override suspend fun getStoreLocation(search: String): Result<List<LocationEntity>> =
        runCatching {
            reportDataSource.getLocations(search).data.toEntity()
        }

    override suspend fun getReportCount(): Result<CountEntity> =
        runCatching {
            reportDataSource.getReportsCount().data.toEntity()
        }

    override suspend fun getStoreValidate(body: ValidateStoreRequestEntity): Result<Unit> =
        runCatching {
            reportDataSource.getStoreValidate(body.toDto())
        }

    override suspend fun getCategories(): Result<List<CategoryEntity>> = runCatching {
        reportDataSource.getCategories().data.toEntity()
    }

    override suspend fun postReport(
        image: String?,
        request: ReportStoreRequestEntity,
    ): Result<GeneratedStoreResponseEntity> {
        val contentRequestBody =
            Json.encodeToString(request.toDto()).toRequestBody("application/json".toMediaType())

        return runCatching {
            reportDataSource.postReport(
                image = if (image == null) image else ContentUriRequestBody(
                    context,
                    Uri.parse(image)
                ).toFormData("image"),
                request = contentRequestBody
            ).data.toEntity()
        }
    }

    override suspend fun getMyUniversity(): Result<UniversityResponseEntity> = runCatching {
        reportDataSource.getMyUniversity().data.toEntity()
    }
}
