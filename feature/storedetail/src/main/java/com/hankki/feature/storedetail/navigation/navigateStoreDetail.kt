package com.hankki.feature.storedetail.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.hankki.core.navigation.MainTabRoute
import com.hankki.feature.storedetail.editbottomsheet.add.AddMenuRoute
import com.hankki.feature.storedetail.editbottomsheet.edit.EditMenuRoute
import com.hankki.feature.storedetail.editbottomsheet.edit.EditModRoute
import com.hankki.feature.storedetail.editbottomsheet.edit.EditModSucceedRoute
import com.hankki.feature.storedetail.StoreDetailRoute
import com.hankki.feature.storedetail.StoreDetailViewModel
import com.hankki.feature.storedetail.editbottomsheet.add.AddMenuSuccessRoute
import com.hankki.feature.storedetail.editbottomsheet.edit.DeleteSuccessRoute
import kotlinx.serialization.Serializable

@Serializable
data class StoreDetail(
    val storeId: Long,
) : MainTabRoute

fun NavController.navigateStoreDetail(storeId: Long, navOptions: NavOptions?) {
    navigate(StoreDetail(storeId = storeId), navOptions)
}

fun NavGraphBuilder.storeDetailNavGraph(
    navController: NavController,
    navigateUp: () -> Unit,
    navigateToAddNewJogbo: () -> Unit,
    onShowSnackBar: (String, Long) -> Unit,
    onShowTextSnackBar: (String) -> Unit,
    navigateToAddMenu: (Long) -> Unit,
    navigateToEditMenu: (Long) -> Unit,
    navigateToEditMod: (Long, Long, String, String) -> Unit,
) {
    // Store Detail
    composable<StoreDetail> { backStackEntry ->
        val items = backStackEntry.toRoute<StoreDetail>()
        StoreDetailRoute(
            storeId = items.storeId,
            navigateUp = {
            },
            navigateToAddNewJogbo = navigateToAddNewJogbo,
            onShowSnackBar = onShowSnackBar,
            onShowTextSnackBar = onShowTextSnackBar,
            onAddMenuClick = { navigateToAddMenu(items.storeId) },
            onEditMenuClick = { navigateToEditMenu(items.storeId) }
        )
    }

    // Add Menu
    composable(
        route = StoreDetailScreen.AddMenu.route,
        arguments = listOf(navArgument("storeId") { type = NavType.LongType })
    ) { backStackEntry ->
        val storeId = backStackEntry.arguments?.getLong("storeId") ?: 0L
        AddMenuRoute(
            storeId = storeId,
            onNavigateUp = navigateUp,
            onNavigateToSuccess = { successStoreId ->
                navController.navigate(
                    StoreDetailScreen.AddMenuSuccess.route.replace(
                        "{storeId}",
                        successStoreId.toString()
                    )
                ) {
                    popUpTo(
                        StoreDetailScreen.Detail.route.replace(
                            "{storeId}",
                            successStoreId.toString()
                        )
                    ) {
                        inclusive = false
                    }
                }
            }
        )
    }

    // Edit Menu
    composable(
        route = StoreDetailScreen.EditMenu.route,
        arguments = listOf(navArgument("storeId") { type = NavType.LongType })
    ) { backStackEntry ->
        val storeId = backStackEntry.arguments?.getLong("storeId") ?: 0L
        EditMenuRoute(
            storeId = storeId,
            onNavigateUp = navigateUp,
            onMenuSelected = { /* Handle Menu Selection */ },
            onEditModClick = { menuId, menuName, price ->
                navigateToEditMod(storeId, menuId, menuName, price)
            },
            onNavigateToDeleteSuccess = { successStoreId ->
                navController.navigate(
                    StoreDetailScreen.DeleteSuccess.route.replace(
                        "{storeId}",
                        successStoreId.toString()
                    )
                ) {
                    popUpTo(
                        StoreDetailScreen.Detail.route.replace(
                            "{storeId}",
                            successStoreId.toString()
                        )
                    ) {
                        inclusive = false
                    }
                }
            }
        )
    }

    // Edit Mod
    composable(
        route = StoreDetailScreen.EditMod.route,
        arguments = listOf(
            navArgument("storeId") { type = NavType.LongType },
            navArgument("menuId") { type = NavType.LongType },
            navArgument("menuName") { type = NavType.StringType },
            navArgument("price") { type = NavType.StringType }
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
            onNavigateUp = {
                navController.navigateStoreDetail(
                    storeId = storeId,
                    navOptions = navOptions {
                        popUpTo("home") {
                            inclusive = false
                        }
                    }
                )
            },
            onMenuUpdated = { updatedMenuName, updatedPrice ->
                viewModel.updateMenu(storeId, menuId, updatedMenuName, updatedPrice.toInt())
                navController.navigate(
                    StoreDetailScreen.EditModSuccess.route.replace(
                        "{storeId}",
                        storeId.toString()
                    )
                ) {
                    popUpTo(
                        StoreDetailScreen.Detail.route.replace(
                            "{storeId}",
                            storeId.toString()
                        )
                    ) {
                        inclusive = false
                    }
                }
            },
            navigateToEditSuccess = { successStoreId ->
                navController.navigate(
                    StoreDetailScreen.EditModSuccess.route.replace(
                        "{storeId}",
                        successStoreId.toString()
                    )
                ) {
                    popUpTo(
                        StoreDetailScreen.Detail.route.replace(
                            "{storeId}",
                            successStoreId.toString()
                        )
                    ) {
                        inclusive = false
                    }
                }
            }
        )
    }

    // Edit Success
    composable(
        route = StoreDetailScreen.EditModSuccess.route,
        arguments = listOf(navArgument("storeId") { type = NavType.LongType })
    ) { backStackEntry ->
        val storeId = backStackEntry.arguments?.getLong("storeId") ?: 0L
        EditModSucceedRoute(
            onNavigateToStoreDetailRoute = {
                navController.navigateStoreDetail(
                    storeId = storeId,
                    navOptions = navOptions {
                        popUpTo("home") {
                            inclusive = false
                        }
                    }
                )
            },
            onNavigateToEditMenu = {
                navController.navigate(
                    StoreDetailScreen.EditMenu.route.replace(
                        "{storeId}",
                        storeId.toString()
                    )
                ) {
                    popUpTo(
                        StoreDetailScreen.Detail.route.replace(
                            "{storeId}",
                            storeId.toString()
                        )
                    ) {
                        inclusive = false
                    }
                }
            },
            onNavigateUp = {
                navController.navigateStoreDetail(
                    storeId = storeId,
                    navOptions = navOptions {
                        popUpTo("home") {
                            inclusive = false
                        }
                    }
                )
            }
        )
    }

    // Delete Success
    composable(
        route = StoreDetailScreen.DeleteSuccess.route,
        arguments = listOf(navArgument("storeId") { type = NavType.LongType })
    ) { backStackEntry ->
        val storeId = backStackEntry.arguments?.getLong("storeId") ?: 0L
        DeleteSuccessRoute(
            onNavigateToStoreDetailRoute = {
                navController.navigateStoreDetail(
                    storeId = storeId,
                    navOptions = navOptions {
                        popUpTo("home") {
                            inclusive = false
                        }
                    }
                )
            },
            onNavigateToEditMenu = {
                navController.navigate(
                    StoreDetailScreen.EditMenu.route.replace(
                        "{storeId}",
                        storeId.toString()
                    )
                ) {
                    popUpTo(
                        StoreDetailScreen.Detail.route.replace(
                            "{storeId}",
                            storeId.toString()
                        )
                    ) {
                        inclusive = false
                    }
                }
            },
            onNavigateUp = {
                navController.navigateStoreDetail(
                    storeId = storeId,
                    navOptions = navOptions {
                        popUpTo("home") {
                            inclusive = false
                        }
                    }
                )
            }
        )
    }

    // AddMenuSuccess
    composable(
        route = StoreDetailScreen.AddMenuSuccess.route,
        arguments = listOf(navArgument("storeId") { type = NavType.LongType })
    ) { backStackEntry ->
        val storeId = backStackEntry.arguments?.getLong("storeId") ?: 0L
        AddMenuSuccessRoute(
            onNavigateToStoreDetailRoute = {
                navController.navigateStoreDetail(
                    storeId = storeId,
                    navOptions = navOptions {
                        popUpTo("home") {
                            inclusive = false
                        }
                    }
                )
            },
            onNavigateToAddMenu = {
                navController.navigate(
                    StoreDetailScreen.AddMenu.route.replace(
                        "{storeId}",
                        storeId.toString()
                    )
                ) {
                    popUpTo(
                        StoreDetailScreen.Detail.route.replace(
                            "{storeId}",
                            storeId.toString()
                        )
                    ) {
                        inclusive = false
                    }
                }
            }
        )
    }
}
