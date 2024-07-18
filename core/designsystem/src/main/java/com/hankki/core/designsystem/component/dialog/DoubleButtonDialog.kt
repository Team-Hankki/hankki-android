package com.hankki.core.designsystem.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hankki.core.designsystem.component.button.HankkiButton
import com.hankki.core.designsystem.component.button.HankkiTextButton
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.White

@Composable
fun DoubleButtonDialog(
    title: String,
    description: String? = null,
    negativeButtonTitle: String,
    positiveButtonTitle: String,
    onNegativeButtonClicked: () -> Unit,
    onPositiveButtonClicked: () -> Unit,
) {
    Dialog(onDismissRequest = onNegativeButtonClicked) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = title,
                    style = HankkiTheme.typography.sub1,
                    color = Gray900,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                if (description != null) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = description,
                        style = HankkiTheme.typography.body4,
                        color = Gray500,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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
fun DoubleButtonDialogPreview() {
    HankkijogboTheme {
        DoubleButtonDialog(
            title = "Title",
            description = "Description",
            negativeButtonTitle = "Negative",
            positiveButtonTitle = "Positive",
            onNegativeButtonClicked = {},
            onPositiveButtonClicked = {}
        )
    }
}
