package com.hankki.domain.home.entity.response

interface CategoryEntity

data class CategoriesEntity(
    val name: String,
    val tag: String
): CategoryEntity

data class CategoryResponseEntity(
    val name: String,
    val tag: String,
    val imageUrl: String
): CategoryEntity
