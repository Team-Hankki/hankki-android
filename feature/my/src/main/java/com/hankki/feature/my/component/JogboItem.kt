package com.hankki.feature.my.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray850
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R

@Composable
fun JogboItem(
    modifier: Modifier = Modifier,
    isEditMode: MutableState<Boolean> = mutableStateOf(false),
    jogboTitle: String = "",
    isJogboSelected: Boolean = false,
    editJogbo: () -> Unit = {},
    moveToJogbo: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Gray850)
    ) {
        Column(
            modifier = if (!isEditMode.value) Modifier
                .wrapContentSize()
                .clickable(onClick = moveToJogbo)
            else Modifier
                .wrapContentSize()
                .clickable(onClick = editJogbo),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Text(
                    text = "\n\n",
                    style = HankkiTheme.typography.body3,
                    color = Color.Transparent,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp, start = 17.dp)
                )

                Text(
                    text = jogboTitle,
                    style = HankkiTheme.typography.body3,
                    color = White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp, start = 17.dp, end = 25.dp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_my_jogbo),
                contentDescription = null
            )
        }
        if (isJogboSelected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(2.dp, Red, shape = RoundedCornerShape(12.dp))
                    .background(Red.copy(alpha = 0.4f))
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
fun ItemPrev() {
    val isSelected = remember { mutableStateOf(true) }

    HankkijogboTheme {
        JogboItem(
            jogboTitle = "새로운 족보 리스트 추가하기",
            isJogboSelected = isSelected.value,
            moveToJogbo = {
                isSelected.value = !isSelected.value
            }
        )
    }
}