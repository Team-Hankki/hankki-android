package com.hankki.domain.home.repository

import com.hankki.domain.home.entity.response.CategoryResponseEntity
import com.hankki.domain.home.entity.response.CategoriesEntity
import com.hankki.domain.home.entity.response.UniversityResponseEntity

interface HomeRepository {
    suspend fun getMyUniversity(): Result<UniversityResponseEntity>
    suspend fun getCategories(): Result<List<CategoryResponseEntity>>
    suspend fun getPriceCategories(): Result<List<CategoriesEntity>>
    suspend fun getSortCategories(): Result<List<CategoriesEntity>>
}
