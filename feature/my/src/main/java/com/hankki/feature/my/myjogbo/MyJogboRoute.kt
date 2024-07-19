package com.hankki.feature.my.myjogbo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.my.entity.response.MyJogboEntity
import com.hankki.feature.my.R
import com.hankki.feature.my.component.AddJogboItem
import com.hankki.feature.my.component.JogboItem
import com.hankki.feature.my.myjogbo.model.MyJogboModel
import com.hankki.feature.my.myjogbo.model.toMyJogboModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MyJogboRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToJogboDetail: (Long) -> Unit,
    navigateToNewJogbo: () -> Unit,
    myJogboViewModel: MyJogboViewModel = hiltViewModel()
) {
    val myJogboState by myJogboViewModel.myJogboState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        myJogboViewModel.getMyJogboList()
    }

    MyJogboScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateToJogboDetail = navigateToJogboDetail,
        navigateToNewJogbo = navigateToNewJogbo,
        jogboItems = myJogboState.myJogboItems,
        editMode = myJogboState.editMode.value,
        updateEditMode = myJogboViewModel::updateMode,
        updateJogboSelectedState = myJogboViewModel::updateJogboSeleted,
        resetJogboState = myJogboViewModel::resetJogboState,
        dialogState = myJogboState.showDialog,
        updateToDialogState = myJogboViewModel::updateToDialogState
    )
}

@Composable
fun MyJogboScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToJogboDetail: (Long) -> Unit,
    navigateToNewJogbo: () -> Unit,
    jogboItems: PersistentList<MyJogboModel>,
    editMode: Boolean = false,
    updateEditMode: () -> Unit,
    updateJogboSelectedState: (Int, Boolean) -> Unit,
    resetJogboState: () -> Unit,
    dialogState: Boolean,
    updateToDialogState: (Boolean) -> Unit
) {
    if (dialogState) {
        DoubleButtonDialog(
            title = stringResource(R.string.ask_delete_jogbo),
            negativeButtonTitle = stringResource(id = R.string.close),
            positiveButtonTitle = stringResource(id = R.string.do_delete),
            onNegativeButtonClicked = { updateToDialogState(false) },
            onPositiveButtonClicked = { /*TODO*/ }
        )
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HankkiTopBar(
            leadingIcon = {
                Icon(
                    painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 9.dp)
                        .size(44.dp)
                        .noRippleClickable(if (editMode) resetJogboState else navigateUp)
                )
            },
            content = {
                Text(
                    text = stringResource(R.string.my_store_jogbo),
                    style = HankkiTheme.typography.sub3,
                    color = Gray900
                )
            },
            trailingIcon = {
                Text(
                    text = if (editMode) stringResource(R.string.delete) else stringResource(R.string.edit),
                    style = HankkiTheme.typography.body1,
                    color = Gray800,
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 14.dp)
                        .padding(end = 8.dp)
                        .run {
                            if (editMode) noRippleClickable { updateToDialogState(true) }
                            else noRippleClickable(updateEditMode)
                        }
                )
            }
        )

        Spacer(modifier = Modifier.height(18.dp))

        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 22.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(17.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AddJogboItem(
                    isEditMode = editMode,
                    onClick = navigateToNewJogbo
                )
            }

            items(jogboItems.size) { index ->
                val jogbo = jogboItems[index]
                JogboItem(
                    id = jogbo.jogboId,
                    title = jogbo.jogboName,
                    image = jogbo.jogboImage,
                    isEditMode = editMode,
                    isSelected = jogbo.jogboSelected,
                    editJogbo = { updateJogboSelectedState(index, !jogbo.jogboSelected) },
                    navigateToJogboDetail = { navigateToJogboDetail(jogbo.jogboId) }
                )
            }
        }
    }
}

@Composable
@Preview
fun MyJogboScreenPreview() {
    HankkijogboTheme {
        MyJogboScreen(
            navigateUp = {},
            navigateToJogboDetail = {_->},
            navigateToNewJogbo = {},
            paddingValues = PaddingValues(),
            jogboItems = persistentListOf(
                MyJogboEntity(1, "TYPE_ONE", "성대쪽문\n가성비 맛집\n진짜 추천드림요1").toMyJogboModel(),
                MyJogboEntity(2, "TYPE_TWO", "성대쪽문\n가성비 맛집\n진짜 추천드림요2").toMyJogboModel()
            ),
            updateEditMode = {},
            updateJogboSelectedState = { _, _ -> },
            resetJogboState = {},
            dialogState = false,
            updateToDialogState = {}
        )
    }
}

