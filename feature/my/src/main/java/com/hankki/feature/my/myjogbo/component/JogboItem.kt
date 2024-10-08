package com.hankki.feature.my.myjogbo.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red500

@Composable
fun JogboItem(
    id: Long,
    title: String,
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    isSelected: Boolean = false,
    editJogbo: () -> Unit,
    navigateToJogboDetail: (Long) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Gray100)
            .run {
                if (!isEditMode) noRippleClickable(onClick = { navigateToJogboDetail(id) })
                else noRippleClickable(onClick = editJogbo)
            }
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Column {
                    JogboItemText(
                        text = "\n\n",
                        color = Color.Transparent
                    )
                }
                JogboItemText(
                    text = title,
                    color = Gray800
                )
            }
            Image(
                imageVector = ImageVector.vectorResource(id = image),
                contentDescription = "jogbo image",
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(
                        width = 2.dp,
                        color = Red500,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = com.hankki.core.designsystem.R.drawable.ic_check_btn),
                    contentDescription = "check button",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(
                            top = 14.dp,
                            end = 14.dp
                        )
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
@Preview
fun JogboItemPreview() {
    val isSelected = remember { mutableStateOf(true) }

    HankkijogboTheme {
        JogboItem(
            id = 0,
            title = "새로운 족보 리스트 추가하기",
            image = 0,
            isSelected = isSelected.value,
            editJogbo = {},
            navigateToJogboDetail = {}
        )
    }
}
