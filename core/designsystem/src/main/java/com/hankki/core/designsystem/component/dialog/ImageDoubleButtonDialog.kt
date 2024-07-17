package com.hankki.core.designsystem.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.HankkiTextButton
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun ImageDoubleButtonDialog(
    title: String,
    description: String,
    negativeButtonTitle: String,
    positiveButtonTitle: String,
    onNegativeButtonClicked: () -> Unit,
    onPositiveButtonClicked: () -> Unit,
) {
    Dialog(onDismissRequest = onNegativeButtonClicked) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
        ) {
            Column(
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 22.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.img_charactor),
                    contentDescription = "charactor"
                )

                Spacer(modifier = Modifier.height(26.dp))

                Text(
                    text = title,
                    style = HankkiTheme.typography.sub1,
                    color = Gray900
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = description,
                    style = HankkiTheme.typography.body4,
                    color = Gray500
                )

                Spacer(modifier = Modifier.height(26.dp))

                Row {
                    HankkiTextButton(
                        text = negativeButtonTitle,
                        onClick = onNegativeButtonClicked,
                        enabled = true,
                        textStyle = HankkiTheme.typography.sub3
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    HankkiButton(
                        text = positiveButtonTitle,
                        onClick = onPositiveButtonClicked,
                        enabled = true,
                        textStyle = HankkiTheme.typography.sub3
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageDoubleButtonDialogPreview() {
    HankkijogboTheme {
        ImageDoubleButtonDialog(
            title = "변동사항을 알려주셔서 감사합니다 :)\n" +
                    "오늘도 저렴하고 든든한 식사하세요!",
            description = "정말 로그아웃 하실 건가요?",
            negativeButtonTitle = "Negative",
            positiveButtonTitle = "Positive",
            onNegativeButtonClicked = {},
            onPositiveButtonClicked = {}
        )
    }
}