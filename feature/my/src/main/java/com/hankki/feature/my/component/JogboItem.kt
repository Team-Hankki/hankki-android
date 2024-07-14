package com.hankki.feature.my.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.feature.my.R

@Composable
fun JogboItem(
    title: String,
    image: String,
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    isSelected: Boolean = false,
    editJogbo: () -> Unit,
    navigateToJogboDetail: () -> Unit
) {
    Box(
        modifier = if (!isEditMode) modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Gray100)
            .noRippleClickable(onClick = navigateToJogboDetail)
        else modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Gray100)
            .noRippleClickable(onClick = editJogbo)
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
                    Image(
                        painter = painterResource(id = R.drawable.ic_add_jogbo_disable),
                        contentDescription = null,
                        modifier = Modifier.alpha(0f)
                    )
                }
                JogboItemText(
                    text = title,
                    color = Gray800
                )
            }
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = modifier.fillMaxWidth()
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(
                        width = 2.dp,
                        color = Red,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = com.hankki.core.designsystem.R.drawable.ic_check_btn),
                    contentDescription = "check button",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.TopEnd)
                        .padding(
                            top = 14.dp,
                            end = 13.dp
                        )
                )
            }
        }
    }
}

@Composable
@Preview
fun ItemPreview() {
    val isSelected = remember { mutableStateOf(true) }

    HankkijogboTheme {
        JogboItem(
            title = "새로운 족보 리스트 추가하기",
            image = "",
            isSelected = isSelected.value,
            editJogbo = {},
            navigateToJogboDetail = {}
        )
    }
}
