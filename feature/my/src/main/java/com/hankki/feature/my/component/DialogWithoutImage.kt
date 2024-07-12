package com.hankki.feature.my.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White

@Composable
fun DialogWithoutImage(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    imageDescription: String
) {
    val dialogContent = getDialogContent(imageDescription)

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .wrapContentSize(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 45.dp)
                ) {
                    Text(
                        text = dialogContent.title,
                        style = HankkiTheme.typography.sub1,
                        color = Gray900,
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = dialogContent.subTitle,
                        style = HankkiTheme.typography.body4,
                        color = Gray500,
                        textAlign = TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "돌아가기",
                            modifier = Modifier
                                .padding(vertical = 10.dp, horizontal = 22.dp)
                                .noRippleClickable(onClick = onConfirmation)
                                .clip(RoundedCornerShape(16.dp)),
                            style = HankkiTheme.typography.sub3,
                            textAlign = TextAlign.Center,
                            color = Red
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { onDismissRequest() },
                            colors = ButtonDefaults.buttonColors(containerColor = Red),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(
                                vertical = 15.dp,
                                horizontal = 22.dp
                            )
                        ) {
                            Text(
                                dialogContent.buttonTitle,
                                style = HankkiTheme.typography.sub3,
                                color = White
                            )
                        }
                    }
                }
            }
        }
    }
}
