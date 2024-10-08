package com.hankki.feature.storedetail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.storedetail.EditMenuRoute
import com.hankki.feature.storedetail.EditModRoute
import com.hankki.feature.storedetail.StoreDetailRoute
import com.hankki.feature.storedetail.StoreDetailViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateStoreDetail(storeId: Long, navOptions: NavOptions?) {
    navigate(StoreDetail(storeId = storeId), navOptions)
}

fun NavGraphBuilder.storeDetailNavGraph(
    navigateUp: () -> Unit,
    navigateToAddNewJogbo: () -> Unit,
    onShowSnackBar: (String, Long) -> Unit,
    onShowTextSnackBar: (String) -> Unit,
    navigateToAddMenu: (Long) -> Unit,
    navigateToEditMenu: (Long) -> Unit,
    navigateToEditMod: (Long, Long, String, String) -> Unit
) {
    composable<StoreDetail> { backStackEntry ->
        val items = backStackEntry.toRoute<StoreDetail>()
        StoreDetailRoute(
            storeId = items.storeId,
            navigateUp = navigateUp,
            navigateToAddNewJogbo = navigateToAddNewJogbo,
            onShowSnackBar = onShowSnackBar,
            onShowTextSnackBar = onShowTextSnackBar,
            onAddMenuClick = { navigateToAddMenu(items.storeId) },
            onEditMenuClick = { navigateToEditMenu(items.storeId) }
        )
    }

    composable(
        route = "add_menu_route/{storeId}",
        arguments = listOf(navArgument("storeId") { type = NavType.LongType })
    ) { backStackEntry ->
        val storeId = backStackEntry.arguments?.getLong("storeId") ?: 0L
        EditMenuRoute(
            storeId = storeId,
            onMenuSelected = { /* Handle Menu Selection */ },
            onDeleteMenuClick = { /* Handle Delete */ },
            onEditModClick = { menuId, menuName, price ->
                navigateToEditMod(storeId, menuId, menuName, price)
            },
            onNavigateUp = navigateUp,
        )
    }


    composable(
        route = "modify_menu_route/{storeId}/{menuId}/{menuName}/{price}",
        arguments = listOf(
            navArgument("storeId") { type = NavType.LongType },
            navArgument("menuId") { type = NavType.LongType },
            navArgument("menuName") { type = NavType.StringType },
            navArgument("price") { type = NavType.StringType },
        )
    ) { backStackEntry ->
        val storeId = backStackEntry.arguments?.getLong("storeId") ?: 0L
        val menuId = backStackEntry.arguments?.getLong("menuId") ?: 0L
        val menuName = backStackEntry.arguments?.getString("menuName") ?: ""
        val price = backStackEntry.arguments?.getString("price") ?: ""
        val viewModel: StoreDetailViewModel = hiltViewModel()

        EditModRoute(
            storeId = storeId,
            menuId = menuId,
            menuName = menuName,
            price = price,
            onNavigateUp = navigateUp,
            onMenuUpdated = { updatedMenuName, updatedPrice ->
                viewModel.updateMenu(storeId, menuId, updatedMenuName, updatedPrice.toInt())
            }
        )
    }


}

@Serializable
data class StoreDetail(
    val storeId: Long,
) : MainTabRoute
