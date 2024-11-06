package com.hankki.feature.storedetail.navigation

sealed class StoreDetailScreen(val route: String) {
    data object Detail : StoreDetailScreen("store_detail_route/{storeId}")
    data object AddMenu : StoreDetailScreen("add_menu_route/{storeId}")
    data object AddMenuSuccess : StoreDetailScreen("add_menu_success_route/{storeId}")
    data object EditMenu : StoreDetailScreen("edit_menu_route/{storeId}")
    data object EditMod : StoreDetailScreen("edit_mod_route/{storeId}/{menuId}/{menuName}/{price}")
    data object EditModSuccess : StoreDetailScreen("edit_mod_success_route/{storeId}")
    data object DeleteSuccess : StoreDetailScreen("delete_success_route/{storeId}")
}