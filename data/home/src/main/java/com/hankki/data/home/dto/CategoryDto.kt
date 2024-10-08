package com.hankki.data.home.dto

import com.hankki.domain.home.entity.response.CategoryResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    CategoryResponseEntity(
        name = it.name,
        tag = it.tag,
        imageUrl = it.imageUrl
    )
}
