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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.component.topappbar.HankkiTopBar
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.my.entity.MyJogboEntity
import com.hankki.feature.my.R
import com.hankki.feature.my.component.AddJogboItem
import com.hankki.feature.my.component.DialogWithButton
import com.hankki.feature.my.component.JogboItem
import com.hankki.feature.my.myjogbo.model.MyJogboModel
import com.hankki.feature.my.myjogbo.model.toMyJogboModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MyJogboRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToJogboDetail: () -> Unit,
    myJogboViewMidel: MyJogboViewModel = hiltViewModel()
) {
    val myJogboState by myJogboViewMidel.myJogboState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        myJogboViewMidel.getMockJogboList()
    }

    MyJogboScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateToJogboDetail = navigateToJogboDetail,
        jogboItems = myJogboState.myJogboItems,
        editMode = myJogboState.editMode.value,
        updateEditMode = myJogboViewMidel::updateMode,
        updateJogboSelectedState = { index, isjogboSelected ->
            myJogboViewMidel.updateJogboSeleted(
                index,
                isjogboSelected
            )
        },
        resetJogboState = myJogboViewMidel::resetJogboState
    )
}

@Composable
fun MyJogboScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToJogboDetail: () -> Unit,
    jogboItems: PersistentList<MyJogboModel>,
    editMode: Boolean = false,
    updateEditMode: () -> Unit,
    updateJogboSelectedState: (Int, Boolean) -> Unit,
    resetJogboState: () -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        DialogWithButton(
            onDismissRequest = { showDialog.value = false },
            onConfirmation = { showDialog.value = false },
            title = stringResource(R.string.ask_delete_jogbo),
            textButtonTitle = stringResource(id = R.string.close),
            buttonTitle = stringResource(id = R.string.do_delete)
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
                        .noRippleClickable( if(editMode) resetJogboState else navigateUp)
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
                    modifier = if (editMode) Modifier
                        .padding(vertical = 12.dp, horizontal = 14.dp)
                        .padding(end = 8.dp)
                        .noRippleClickable(onClick = {
                            showDialog.value = true
                        })
                    else Modifier
                        .padding(vertical = 12.dp, horizontal = 14.dp)
                        .padding(end = 8.dp)
                        .noRippleClickable(onClick = updateEditMode)
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
                AddJogboItem(isEditMode = editMode)
            }

            items(jogboItems.size) { index ->
                val jogbo = jogboItems[index]
                JogboItem(
                    modifier = if (editMode)
                        Modifier.noRippleClickable(onClick = {})
                    else
                        Modifier.noRippleClickable(onClick = navigateToJogboDetail),
                    title = jogbo.jogboName,
                    image = jogbo.jogboImage,
                    isEditMode = editMode,
                    isSelected = jogbo.jogboSelected,
                    editJogbo = { updateJogboSelectedState(index, !jogbo.jogboSelected) },
                    navigateToJogboDetail = navigateToJogboDetail
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
            navigateToJogboDetail = {},
            paddingValues = PaddingValues(),
            jogboItems = persistentListOf(
                MyJogboEntity(1, "", "성대쪽문\n가성비 맛집\n진짜 추천드림요1").toMyJogboModel(),
                MyJogboEntity(2, "", "성대쪽문\n가성비 맛집\n진짜 추천드림요2").toMyJogboModel()
            ),
            updateEditMode = {},
            updateJogboSelectedState = { _, _ -> },
            resetJogboState = {}
        )
    }
}

