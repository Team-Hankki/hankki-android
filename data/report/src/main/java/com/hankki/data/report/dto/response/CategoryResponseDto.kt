package com.hankki.data.report.dto.response

import com.hankki.domain.report.entity.CategoryEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponseDto(
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

fun CategoriesResponseDto.toEntity() = categories.map {
    CategoryEntity(
        name = it.name,
        tag = it.tag,
        imageUrl = it.imageUrl
    )
}
