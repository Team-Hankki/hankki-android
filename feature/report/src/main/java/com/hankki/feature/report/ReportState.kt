package com.hankki.feature.report

import com.hankki.domain.report.entity.CategoryEntity
import com.hankki.feature.report.model.MenuModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ReportState(
    val selectedCategory: String? = null,
    val categoryList: PersistentList<CategoryEntity> = persistentListOf(),
    val menuList: PersistentList<MenuModel> = persistentListOf(MenuModel("", "")),
)
