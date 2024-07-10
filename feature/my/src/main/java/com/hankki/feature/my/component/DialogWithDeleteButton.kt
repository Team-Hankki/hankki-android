package com.hankki.feature.my.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White
import com.hankki.feature.my.R

@Composable
fun DialogWithDeleteButton(
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
    dialogTitle: String = ""
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = dialogTitle,
                    style = HankkiTheme.typography.sub1,
                    color = Gray900,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.close),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(White)
                            .padding(vertical = 15.dp, horizontal = 36.dp)
                            .clickable(onClick = onDismissRequest),
                        style = HankkiTheme.typography.sub3,
                        textAlign = TextAlign.Center,
                        color = Red
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onConfirmation() },
                        colors = ButtonDefaults.buttonColors(containerColor = Red),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(
                            vertical = 15.dp,
                            horizontal = 22.dp
                        )
                    ) {
                        Text(
                            stringResource(R.string.do_delete),
                            style = HankkiTheme.typography.sub3,
                            color = White
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun DialogWithDeleteButtonPrev() {
    HankkijogboTheme {
        DialogWithDeleteButton(dialogTitle = "족보를 삭제할까요?")
    }
}
