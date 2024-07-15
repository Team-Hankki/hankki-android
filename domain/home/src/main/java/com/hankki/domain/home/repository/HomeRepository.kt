package com.hankki.domain.home.repository

import com.hankki.domain.home.entity.response.CategoryEntity
import com.hankki.domain.home.entity.response.CategoriesEntity

interface HomeRepository {
    suspend fun getCategories(): Result<List<CategoryEntity>>
    suspend fun getPriceCategories(): Result<List<CategoriesEntity>>
    suspend fun getSortCategories(): Result<List<CategoriesEntity>>
}
