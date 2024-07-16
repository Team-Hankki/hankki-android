package com.hankki.data.home.dto

import com.hankki.domain.home.entity.response.CategoriesEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SortCategoriesResponse(
    @SerialName("options")
    val options: List<CategoryDto>,
) {
    @Serializable
    data class CategoryDto(
        @SerialName("name")
        val name: String,
        @SerialName("tag")
        val tag: String
    )
}

fun SortCategoriesResponse.toEntity() = options.map {
    CategoriesEntity(
        name = it.name,
        tag = it.tag
    )
}
