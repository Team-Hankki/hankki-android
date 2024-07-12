package com.hankki.feature.my.myjogbo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White
import com.hankki.domain.my.entity.MyJogboEntity
import com.hankki.feature.my.R
import com.hankki.feature.my.component.AddJogboItem
import com.hankki.feature.my.component.JogboItem
import com.hankki.feature.my.component.Title
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
        jogboItems = myJogboState.myJogboItems
    )
}

@Composable
fun MyJogboScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToJogboDetail: () -> Unit,
    jogboItems: PersistentList<MyJogboModel>
) {
    val isEditMode = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Title(
            title = stringResource(id = R.string.my_jogbo),
            isBackButtonUsed = true,
            onBackButtonClicked = navigateUp,
            isEditButtonUsed = true,
            isEditMode = isEditMode.value,
            onEditButtonClicked = { isEditMode.value = !isEditMode.value },
        )

        Spacer(modifier = Modifier.height(18.dp))

        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 22.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(17.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AddJogboItem(isEditMode = isEditMode)
            }

            items(jogboItems) { item ->
                var isSelected by remember { mutableStateOf(item.jogboSelected) }
                JogboItem(
                    isEditMode = isEditMode,
                    jogboTitle = item.jogboName,
                    isJogboSelected = if (isEditMode.value) isSelected else false,
                    editJogbo = {
                        isSelected = !isSelected
                        item.jogboSelected = isSelected
                    },
                    moveToJogbo = navigateToJogboDetail
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
                MyJogboEntity(1, "성대쪽문\n가성비 맛집\n진짜 추천드림요").toMyJogboModel(),
                MyJogboEntity(2, "성대쪽문\n가성비 맛집\n진짜 추천드림요").toMyJogboModel()
            )
        )
    }
}

