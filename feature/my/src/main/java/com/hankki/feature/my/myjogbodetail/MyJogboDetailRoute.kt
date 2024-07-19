package com.hankki.feature.my.myjogbodetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.dialog.SingleButtonDialog
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.my.entity.response.Store
import com.hankki.domain.my.entity.response.UserInformationEntity
import com.hankki.feature.my.R
import com.hankki.feature.my.component.JogboFolder
import com.hankki.feature.my.component.StoreItem
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MyJogboDetailRoute(
    favoriteId: Long,
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    myJogboDetailViewModel: MyJogboDetailViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val myJogboDetailState by myJogboDetailViewModel.myJogboDetailState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        myJogboDetailViewModel.getJogboDetail(favoriteId)
        myJogboDetailViewModel.getUserName()
    }

    LaunchedEffect(myJogboDetailViewModel.mySideEffect, lifecycleOwner) {
        myJogboDetailViewModel.mySideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MyJogboSideEffect.NavigateToDetail -> navigateToDetail(sideEffect.id)
                }
            }
    }

    MyJogboDetailScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        jogboTitle = myJogboDetailState.myStoreItems.title,
        jogboChips = myJogboDetailState.myStoreItems.chips.toPersistentList(),
        storeItems = myJogboDetailState.myStoreItems.stores.toPersistentList(),
        deleteDialogState = myJogboDetailState.showDeleteDialog,
        shareDialogState = myJogboDetailState.showShareDialog,
        userInformation = myJogboDetailState.userInformation,
        updateShareDialogState = { myJogboDetailViewModel.updateShareDialog(myJogboDetailState.showShareDialog) },
        updateDeleteDialogState = { myJogboDetailViewModel.updateDeleteDialog(myJogboDetailState.showDeleteDialog) },
        deleteJogboStore = { storeId ->
            myJogboDetailViewModel.deleteJogboStore(
                favoriteId,
                storeId
            )
        },
        selectedStoreId = myJogboDetailState.selectedStoreId,
        updateSelectedStoreId = { storeId -> myJogboDetailViewModel.updateSelectedStoreId(storeId) },
        onClickStoreItem = { storeId -> myJogboDetailViewModel.onClickStoreItem(storeId) },
        userName = myJogboDetailState.userInformation.nickname
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyJogboDetailScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    jogboTitle: String,
    jogboChips: PersistentList<String>,
    storeItems: PersistentList<Store>,
    deleteDialogState: Boolean,
    shareDialogState: Boolean,
    userInformation: UserInformationEntity,
    updateShareDialogState: () -> Unit,
    updateDeleteDialogState: () -> Unit,
    deleteJogboStore: (Long) -> Unit,
    selectedStoreId: Long,
    updateSelectedStoreId: (Long) -> Unit,
    onClickStoreItem: (Long) -> Unit,
    userName: String
) {
    if (shareDialogState) {
        SingleButtonDialog(
            title = stringResource(R.string.go_to_register_store),
            description = stringResource(R.string.preparing_share_jogbo),
            buttonTitle = stringResource(R.string.check),
            onConfirmation = updateShareDialogState
        )
    }

    if (deleteDialogState) {
        DoubleButtonDialog(
            title = stringResource(R.string.delete_store),
            negativeButtonTitle = stringResource(R.string.go_back),
            positiveButtonTitle = stringResource(id = R.string.delete),
            onNegativeButtonClicked = updateDeleteDialogState,
            onPositiveButtonClicked = {
                deleteJogboStore(selectedStoreId)
            }
        )
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(Red),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HankkiTopBar(
            modifier = Modifier.background(Red),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 9.dp)
                        .size(44.dp)
                        .noRippleClickable(onClick = navigateUp),
                    tint = Color.Unspecified
                )
            },
            content = {
                Text(
                    text = stringResource(R.string.my_store_jogbo),
                    style = HankkiTheme.typography.sub3,
                    color = Gray900
                )
            }
        )

        Spacer(
            modifier = Modifier
                .background(Red)
                .height(4.dp)
        )

        JogboFolder(
            title = jogboTitle,
            chips = jogboChips,
            userName = userInformation.nickname,
            shareJogbo = updateShareDialogState
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(storeItems) { store ->
                StoreItem(
                    imageUrl = store.imageUrl,
                    category = store.category,
                    name = store.name,
                    price = store.lowestPrice,
                    heartCount = store.heartCount,
                    isIconUsed = false,
                    isIconSelected = false,
                    modifier = Modifier.combinedClickable(
                        onClick = { onClickStoreItem(store.id) },
                        onLongClick = {
                            updateSelectedStoreId(store.id)
                            updateDeleteDialogState()
                        }
                    )
                )
                if (storeItems.indexOf(store) != storeItems.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 1.dp),
                        thickness = 1.dp,
                        color = Gray200
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .wrapContentSize()
                        .background(Gray100)
                        .padding(12.dp)
                        .padding(end = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "add",
                        modifier = Modifier.size(24.dp),
                        tint = Gray500
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = stringResource(R.string.go_to_store),
                        color = Gray500,
                        style = HankkiTheme.typography.body6,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MyJogboDetailScreenPreview() {

    HankkijogboTheme {
        MyJogboDetailScreen(
            PaddingValues(),
            {},
            "",
            persistentListOf("", "", ""),
            persistentListOf(
                Store(0, "", "", "", 0, 0),
                Store(0, "", "", "", 0, 0),
                Store(0, "", "", "", 0, 0)
            ),
            false,
            false,
            UserInformationEntity(
                nickname = "",
                profileImageUrl = ""
            ),
            {},
            {},
            {},
            0,
            {},
            { _ -> },
            ""
        )
    }
}
