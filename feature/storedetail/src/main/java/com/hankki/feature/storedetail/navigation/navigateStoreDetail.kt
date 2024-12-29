package com.hankki.feature.storedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.storedetail.StoreDetailReportRoute
import com.hankki.feature.storedetail.StoreDetailRoute
import com.hankki.feature.storedetail.editbottomsheet.add.addmenu.AddMenuRoute
import com.hankki.feature.storedetail.editbottomsheet.add.addsuccess.AddMenuSuccessRoute
import com.hankki.feature.storedetail.editbottomsheet.edit.delete.DeleteSuccessLastRoute
import com.hankki.feature.storedetail.editbottomsheet.edit.delete.DeleteSuccessRoute
import com.hankki.feature.storedetail.editbottomsheet.edit.editmenu.EditMenuRoute
import com.hankki.feature.storedetail.editbottomsheet.edit.mod.ModRoute
import com.hankki.feature.storedetail.editbottomsheet.edit.mod.ModSuccessRoute
import kotlinx.serialization.Serializable

fun NavController.navigateStoreDetail(storeId: Long, navOptions: NavOptions? = null) {
    navigate(StoreDetail(storeId = storeId), navOptions)
}

fun NavController.navigateAddMenu(storeId: Long, navOptions: NavOptions? = null) {
    navigate(AddMenu(storeId = storeId), navOptions)
}

fun NavController.navigateAddMenuSuccess(
    storeId: Long,
    submittedMenuCount: Int,
    navOptions: NavOptions? = null
) {
    navigate(AddMenuSuccess(storeId = storeId, submittedMenuCount = submittedMenuCount), navOptions)
}

fun NavController.navigateEditMenu(storeId: Long, navOptions: NavOptions? = null) {
    navigate(EditMenu(storeId = storeId), navOptions)
}

fun NavController.navigateEditMod(
    storeId: Long,
    menuId: Long,
    menuName: String,
    price: String,
    navOptions: NavOptions? = null
) {
    navigate(EditMod(storeId, menuId, menuName, price), navOptions)
}

fun NavController.navigateEditModSuccess(storeId: Long, navOptions: NavOptions? = null) {
    navigate(EditModSuccess(storeId = storeId), navOptions)
}

fun NavController.navigateDeleteSuccess(storeId: Long, navOptions: NavOptions? = null) {
    navigate(DeleteSuccess(storeId = storeId), navOptions)
}

fun NavController.navigateDeleteSuccessLast(storeId: Long, navOptions: NavOptions? = null) {
    navigate(DeleteSuccessLast(storeId = storeId), navOptions)
}

fun NavController.navigateToStoreDetailReport(storeId: Long, navOptions: NavOptions? = null) {
    navigate(StoreDetailReport(storeId = storeId), navOptions)
}

fun NavGraphBuilder.storeDetailNavGraph(
    navController: NavController,
    navigateUp: () -> Unit,
    navigateToAddNewJogbo: () -> Unit,
    navigateToStoreDetailReport: (Long) -> Unit,
    navigateToAddMenu: (Long) -> Unit,
    navigateToEditMenu: (Long) -> Unit,
    navigateToEditMod: (Long, Long, String, String) -> Unit,
    navigateToEditSuccess: (Long) -> Unit,
    navigateToDeleteSuccess: (Long) -> Unit,
    navigateToHome: () -> Unit
) {
    composable<StoreDetail> { backStackEntry ->
        val items = backStackEntry.toRoute<StoreDetail>()
        StoreDetailRoute(
            storeId = items.storeId,
            navigateUp = navigateUp,
            navigateToAddNewJogbo = navigateToAddNewJogbo,
            navigateToStoreDetailReportRoute = navigateToStoreDetailReport,
            onAddMenuClick = { navigateToAddMenu(items.storeId) },
            onEditMenuClick = { navigateToEditMenu(items.storeId) }
        )
    }

    composable<AddMenu> { backStackEntry ->
        val items = backStackEntry.toRoute<AddMenu>()
        AddMenuRoute(
            storeId = items.storeId,
            onNavigateUp = navigateUp,
            onNavigateToSuccess = { menuCount ->
                navController.navigateAddMenuSuccess(
                    items.storeId,
                    submittedMenuCount = menuCount,
                    navOptions {
                        popUpTo(StoreDetail(items.storeId)) { inclusive = false }
                    }
                )
            }
        )
    }

    composable<EditMenu> { backStackEntry ->
        val items = backStackEntry.toRoute<EditMenu>()
        EditMenuRoute(
            storeId = items.storeId,
            onNavigateUp = navigateUp,
            onMenuSelected = { },
            onEditModClick = { menuId, menuName, price ->
                navigateToEditMod(items.storeId, menuId, menuName, price)
            },
            onNavigateToDeleteSuccess = { successStoreId ->
                navigateToDeleteSuccess(successStoreId)
            },
            onNavigateToDeleteSuccessLast = { successStoreId ->
                navController.navigateDeleteSuccessLast(successStoreId, navOptions {
                    popUpTo(StoreDetail(successStoreId)) { inclusive = false }
                })
            }
        )
    }

    composable<EditMod> { backStackEntry ->
        val items = backStackEntry.toRoute<EditMod>()
        ModRoute(
            storeId = items.storeId,
            menuId = items.menuId,
            menuName = items.menuName,
            price = items.price,
            onNavigateUp = {
                navController.navigateStoreDetail(items.storeId, navOptions {
                    popUpTo(StoreDetail(items.storeId)) { inclusive = false }
                })
            },
            onNavigateToEditSuccess = { successStoreId ->
                navigateToEditSuccess(successStoreId)
            },
            onNavigateToDeleteSuccess = { successStoreId ->
                navigateToDeleteSuccess(successStoreId)
            },
        )
    }

    composable<EditModSuccess> { backStackEntry ->
        val items = backStackEntry.toRoute<EditModSuccess>()
        ModSuccessRoute(
            onNavigateToStoreDetailRoute = {
                navController.navigateStoreDetail(items.storeId, navOptions {
                    popUpTo(StoreDetail(items.storeId)) { inclusive = true }
                })
            },
            onNavigateToEditMenu = {
                navController.navigateEditMenu(items.storeId, navOptions {
                    popUpTo(StoreDetail(items.storeId)) { inclusive = false }
                })
            }
        )
    }

    composable<DeleteSuccess> { backStackEntry ->
        val items = backStackEntry.toRoute<DeleteSuccess>()
        DeleteSuccessRoute(
            onNavigateToStoreDetailRoute = {
                navController.navigateStoreDetail(items.storeId, navOptions {
                    popUpTo(StoreDetail(items.storeId)) { inclusive = true }
                })
            },
            onNavigateToEditMenu = {
                navController.navigateEditMenu(items.storeId, navOptions {
                    popUpTo(StoreDetail(items.storeId)) { inclusive = false }
                })
            },
        )
    }

    composable<DeleteSuccessLast> {
        DeleteSuccessLastRoute(
            onNavigateToHome = navigateToHome
        )
    }

    composable<AddMenuSuccess> { backStackEntry ->
        val items = backStackEntry.toRoute<AddMenuSuccess>()
        AddMenuSuccessRoute(
            submittedMenuCount = items.submittedMenuCount,
            onNavigateToStoreDetailRoute = {
                navController.navigateStoreDetail(items.storeId, navOptions {
                    popUpTo(StoreDetail(items.storeId)) { inclusive = true }
                })
            },
            onNavigateToAddMenu = {
                navController.navigateAddMenu(items.storeId, navOptions {
                    popUpTo(StoreDetail(items.storeId)) { inclusive = false }
                })
            }
        )
    }

    composable<StoreDetailReport> { backStackEntry ->
        val items = backStackEntry.toRoute<StoreDetailReport>()
        StoreDetailReportRoute(
            storeId = items.storeId,
            onNavigateUp = { navController.popBackStack() }
        )
    }
}

@Serializable
data class StoreDetail(val storeId: Long) : MainTabRoute

@Serializable
data class AddMenu(val storeId: Long) : MainTabRoute

@Serializable
data class AddMenuSuccess(
    val storeId: Long,
    val submittedMenuCount: Int
) : MainTabRoute

@Serializable
data class EditMenu(val storeId: Long) : MainTabRoute

@Serializable
data class EditMod(
    val storeId: Long,
    val menuId: Long,
    val menuName: String,
    val price: String
) : MainTabRoute

@Serializable
data class EditModSuccess(val storeId: Long) : MainTabRoute

@Serializable
data class DeleteSuccess(val storeId: Long) : MainTabRoute

@Serializable
data class DeleteSuccessLast(val storeId: Long) : MainTabRoute

@Serializable
data class StoreDetailReport(val storeId: Long) : MainTabRoute
