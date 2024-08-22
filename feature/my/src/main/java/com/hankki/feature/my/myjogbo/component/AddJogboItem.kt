package com.hankki.feature.my.myjogbo.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red300
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R

@Composable
fun AddJogboItem(
    isEditMode: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(12.dp))
            .run {
                if (!isEditMode) {
                    background(Red500).noRippleClickable(onClick = onClick)
                } else background(Red300)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        JogboItemText(
            text = stringResource(id = R.string.add_new_jogbo_list),
            color = White
        )
        Image(
            painter =
            if (isEditMode) painterResource(id = R.drawable.ic_add_jogbo_disable)
            else painterResource(id = R.drawable.ic_add_jogbo),
            contentDescription = stringResource(id = R.string.add_new_jogbo_list),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Preview
fun ItemAddJogboPreview() {
    HankkijogboTheme {
        AddJogboItem(isEditMode = true)
    }
}
