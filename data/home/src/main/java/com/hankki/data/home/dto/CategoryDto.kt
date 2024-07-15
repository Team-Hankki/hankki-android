package com.hankki.data.home.dto

import com.hankki.domain.home.entity.response.CategoryEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import timber.log.Timber.Forest.tag

@Serializable
data class CategoriesResponse(
    @SerialName("categories")
    val categories: List<CategoryDto>,
) {
    @Serializable
    data class CategoryDto(
        @SerialName("name")
        val name: String,
        @SerialName("tag")
        val tag: String,
        @SerialName("imageUrl")
        val imageUrl: String,
    )
}

fun CategoriesResponse.toEntity() = categories.map {
    CategoryEntity(
        name = it.name,
        tag = it.tag,
        imageUrl = it.imageUrl
    )
}
