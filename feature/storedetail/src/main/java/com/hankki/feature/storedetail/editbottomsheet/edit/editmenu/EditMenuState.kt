package com.hankki.feature.storedetail.editbottomsheet.edit.editmenu

import com.hankki.domain.storedetail.entity.MenuItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class EditMenuState(
    val menuItems: PersistentList<MenuItem> = persistentListOf(),
    val selectedMenuItem: MenuItem? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val deleteSuccess: Boolean = false
) {
    val menuItemsCount: Int
        get() = menuItems.size
}