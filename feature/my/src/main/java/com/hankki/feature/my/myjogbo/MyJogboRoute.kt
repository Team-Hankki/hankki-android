package com.hankki.feature.my.myjogbo

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.common.utill.UiState
import com.hankki.core.designsystem.component.dialog.DoubleButtonDialog
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray700
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R
import com.hankki.feature.my.component.AddJogboItem
import com.hankki.feature.my.component.JogboItem
import com.hankki.feature.my.myjogbo.model.MyJogboModel
import com.hankki.feature.my.myjogbo.model.transformImage
import kotlinx.collections.immutable.PersistentList

@Composable
fun MyJogboRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToJogboDetail: (Long) -> Unit,
    navigateToNewJogbo: () -> Unit,
    myJogboViewModel: MyJogboViewModel = hiltViewModel(),
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
        state = myJogboState.uiState,
        editMode = myJogboState.editMode,
        updateEditMode = myJogboViewModel::updateMode,
        updateJogboSelectedState = myJogboViewModel::updateJogboSeleted,
        resetJogboState = myJogboViewModel::resetJogboState,
        dialogState = myJogboState.showDialog,
        updateToDialogState = myJogboViewModel::updateToDialogState,
        deleteJogboItems = myJogboViewModel::deleteJogboStore
    )

    BackOnPressed(
        editMode = myJogboState.editMode,
        resetJogboState = myJogboViewModel::resetJogboState,
        navigateUp = navigateUp,
    )
}

@Composable
fun MyJogboScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToJogboDetail: (Long) -> Unit,
    navigateToNewJogbo: () -> Unit,
    state: UiState<PersistentList<MyJogboModel>>,
    editMode: Boolean = false,
    updateEditMode: () -> Unit,
    updateJogboSelectedState: (Int, Boolean) -> Unit,
    resetJogboState: () -> Unit,
    dialogState: Boolean,
    updateToDialogState: (Boolean) -> Unit,
    deleteJogboItems: () -> Unit,
) {
    if (dialogState) {
        DoubleButtonDialog(
            title = stringResource(R.string.ask_delete_jogbo),
            negativeButtonTitle = stringResource(id = R.string.close),
            positiveButtonTitle = stringResource(id = R.string.do_delete),
            onNegativeButtonClicked = { updateToDialogState(false) },
            onPositiveButtonClicked = deleteJogboItems
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
                        .padding(start = 6.dp)
                        .size(44.dp)
                        .noRippleClickable(if (editMode) resetJogboState else navigateUp),
                    tint = Color.Unspecified
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
                    color = Gray700,
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 14.dp)
                        .padding(end = 9.dp)
                        .run {
                            if (editMode) noRippleClickable { updateToDialogState(true) }
                            else noRippleClickable(updateEditMode)
                        }
                )
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        when (state) {
            UiState.Failure -> {}
            UiState.Loading -> {}
            is UiState.Success -> {
                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 22.dp),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        AddJogboItem(
                            isEditMode = editMode,
                            onClick = navigateToNewJogbo
                        )
                    }
                    itemsIndexed(state.data) { index, jogbo ->
                        JogboItem(
                            id = jogbo.jogboId,
                            title = jogbo.jogboName,
                            image = transformImage(jogbo.jogboImage),
                            isEditMode = editMode,
                            isSelected = jogbo.jogboSelected,
                            editJogbo = { updateJogboSelectedState(index, !jogbo.jogboSelected) },
                            navigateToJogboDetail = { navigateToJogboDetail(jogbo.jogboId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BackOnPressed(
    editMode: Boolean = false,
    resetJogboState: () -> Unit,
    navigateUp: () -> Unit,
) {
    BackHandler(onBack = {
        if (editMode)
            resetJogboState()
        else
            navigateUp()
    })
}

@Composable
@Preview
fun MyJogboScreenPreview() {
    HankkijogboTheme {
        MyJogboScreen(
            navigateUp = {},
            navigateToJogboDetail = { _ -> },
            navigateToNewJogbo = {},
            paddingValues = PaddingValues(),
            state = UiState.Loading,
            updateEditMode = {},
            updateJogboSelectedState = { _, _ -> },
            resetJogboState = {},
            dialogState = false,
            updateToDialogState = {},
            deleteJogboItems = {}
        )
    }
}

