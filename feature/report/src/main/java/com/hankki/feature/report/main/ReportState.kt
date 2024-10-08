package com.hankki.feature.report.main

import android.net.Uri
import com.hankki.domain.report.entity.CategoryEntity
import com.hankki.feature.report.model.LocationModel
import com.hankki.feature.report.model.MenuModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class ReportState(
    val universityId: Long = 1,
    val count: Long = 0,
    val location: LocationModel = LocationModel(),
    val storeId: Long = 0,
    val isShowErrorDialog: Boolean = false,
    val isUniversityError: Boolean = false,
    val buttonEnabled: Boolean = false,
    val selectedCategory: String? = null,
    val selectedImageUri: Uri? = null,
    val categoryList: PersistentList<CategoryEntity> = persistentListOf(),
    val menuList: PersistentList<MenuModel> = persistentListOf(MenuModel("", "")),
)
