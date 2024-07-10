package com.hankki.feature.my.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.my.R

@Composable
fun Title(
    title: String,
    modifier: Modifier = Modifier,
    isEditButtonUsed: Boolean = false,
    isEditMode: Boolean = false,
    onEditButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: () -> Unit = {},
    isBackButtonUsed: Boolean = false,
    onBackButtonClicked: () -> Unit = {}
) {
    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        DialogWithDeleteButton(
            onDismissRequest = { showDialog.value = false },
            onConfirmation = { showDialog.value = false },
            dialogTitle = stringResource(R.string.dialog_delete_jogbo)
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (isBackButtonUsed) {
            if (isEditMode) {
                IconButton(
                    onClick = onEditButtonClicked,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 6.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = null
                    )
                }
            } else {
                IconButton(
                    onClick = onBackButtonClicked,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 6.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = null
                    )
                }
            }
        }
        Text(
            text = title,
            color = Gray900,
            style = HankkiTheme.typography.sub3,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 18.dp)
        )
        if (isEditButtonUsed) {
            Text(
                text = if (isEditMode) stringResource(R.string.dialog_delete) else stringResource(R.string.dialog_edit),
                color = Gray600,
                style = HankkiTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = if (isEditMode)
                    Modifier
                        .align(Alignment.CenterEnd)
                        .padding(vertical = 12.dp, horizontal = 14.dp)
                        .noRippleClickable(onClick = { showDialog.value = true })
                else Modifier
                    .align(Alignment.CenterEnd)
                    .padding(vertical = 12.dp, horizontal = 14.dp)
                    .noRippleClickable(onClick = onEditButtonClicked)
            )
        }
    }
}

@Preview
@Composable
fun titlePrev() {
    val isEditMode = remember { mutableStateOf(false) }

    HankkijogboTheme {
        Title(
            title = "나의 족보",
            isBackButtonUsed = true,
            isEditMode = isEditMode.value,
            onDeleteButtonClicked = { isEditMode.value = !isEditMode.value }
        )
    }
}