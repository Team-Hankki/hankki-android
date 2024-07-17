package com.hankki.domain.home.repository

import com.hankki.domain.home.entity.response.CategoriesEntity
import com.hankki.domain.home.entity.response.CategoryEntity
import com.hankki.domain.home.entity.response.StoreEntity
import com.hankki.domain.home.entity.response.UniversityResponseEntity

interface HomeRepository {
    suspend fun getMyUniversity(): Result<UniversityResponseEntity>
    suspend fun getCategories(): Result<List<CategoryEntity>>
    suspend fun getPriceCategories(): Result<List<CategoriesEntity>>
    suspend fun getSortCategories(): Result<List<CategoriesEntity>>
    suspend fun getStores(
        universityId: Long?,
        storeCategory: String?,
        priceCategory: String?,
        sortOption: String?,
    ): Result<List<StoreEntity>>
}
