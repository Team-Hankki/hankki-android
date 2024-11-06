package com.hankki.feature.storedetail.editbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray50
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.feature.storedetail.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMenuBottomSheet(
    onDismissRequest: () -> Unit,
    onAddMenuClick: () -> Unit = {},
    onEditMenuClick: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState,
        containerColor = Gray50,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        dragHandle = {
            Spacer(modifier = Modifier.height(16.dp))
        },
    ) {
        Column()
        {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "어떻게 편집할까요?",
                style = HankkiTheme.typography.suitH2,
                color = Gray900,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 32.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CardItem(
                    iconId = R.drawable.ic_bottom_plus,
                    backgroundImageId = R.drawable.img_add,
                    text = "새로운 메뉴\n추가하기",
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            onDismissRequest()
                            onAddMenuClick()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1.03f)
                )

                CardItem(
                    iconId = R.drawable.ic_bottom_edit,
                    backgroundImageId = R.drawable.img_edit,
                    text = "원래 메뉴\n수정/삭제하기",
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            onDismissRequest()
                            onEditMenuClick()
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1.03f)
                )
            }
        }
    }
}

@Composable
fun CardItem(
    iconId: Int,
    text: String,
    backgroundImageId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Gray200, RoundedCornerShape(16.dp))
            .noRippleClickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = backgroundImageId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(18.dp)
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                style = HankkiTheme.typography.suitH3,
                color = Gray800
            )
        }
    }
}
