package com.hankki.feature.my.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Yellow
import com.hankki.core.designsystem.theme.YellowLight
import com.hankki.feature.my.R

@Composable
fun AddJogboItem(
    isEditMode: MutableState<Boolean>,
    onClick: () -> Unit = {}
) {
    Column(
        modifier =
        if (isEditMode.value) Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(12.dp))
            .background(YellowLight)
        else Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(12.dp))
            .background(Yellow)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.add_new_jogbo_list),
            style = HankkiTheme.typography.body3,
            color = if (isEditMode.value) Gray300 else Gray900,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, start = 17.dp)
        )
        Image(
            painter =
            if (isEditMode.value) painterResource(id = R.drawable.ic_add_jogbo_disable)
            else painterResource(id = R.drawable.ic_add_jogbo_enable),
            contentDescription = stringResource(id = R.string.add_new_jogbo_list),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview
fun ItemAddJogboPrev() {
    HankkijogboTheme {
        AddJogboItem(isEditMode = remember { mutableStateOf(false) })
    }
}