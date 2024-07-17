package com.hankki.data.home.repositoryImpl

import com.hankki.data.home.datasource.HomeDataSource
import com.hankki.data.home.dto.toEntity
import com.hankki.domain.home.entity.response.CategoriesEntity
import com.hankki.domain.home.entity.response.CategoryEntity
import com.hankki.domain.home.entity.response.StoreEntity
import com.hankki.domain.home.entity.response.StorePinEntity
import com.hankki.domain.home.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun getMyUniversity() = runCatching {
        homeDataSource.getMyUniversity().data.toEntity()
    }

    override suspend fun getCategories(): Result<List<CategoryEntity>> = runCatching {
        homeDataSource.getCategories().data.toEntity()
    }

    override suspend fun getPriceCategories(): Result<List<CategoriesEntity>> = runCatching {
        homeDataSource.getPriceCategories().data.toEntity()
    }

    override suspend fun getSortCategories(): Result<List<CategoriesEntity>> = runCatching {
        homeDataSource.getSortCategories().data.toEntity()
    }

    override suspend fun getStores(
        universityId: Long?,
        storeCategory: String?,
        priceCategory: String?,
        sortOption: String?,
    ): Result<List<StoreEntity>> = runCatching {
        homeDataSource.getStores(
            universityId,
            storeCategory,
            priceCategory,
            sortOption
        ).data.toEntity()
    }

    override suspend fun getStoresPins(
        universityId: Long?,
        storeCategory: String?,
        priceCategory: String?,
        sortOption: String?,
    ): Result<List<StorePinEntity>> = runCatching {
        homeDataSource.getStoresPins(
            universityId,
            storeCategory,
            priceCategory,
            sortOption
        ).data.toEntity()
    }

    override suspend fun getStoreThumbnail(storeId: Long): Result<StoreEntity> = runCatching {
        homeDataSource.getStoreThumbnail(storeId).data.toEntity()
    }
}
