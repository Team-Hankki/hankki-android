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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.R
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.core.designsystem.theme.Red
import com.hankki.core.designsystem.theme.White

@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    imageDescription: String
) {
    val dialogContent = getDialogContent(imageDescription)

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .wrapContentSize(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 43.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 45.dp)
                ) {
                    Image(
                        painter = dialogContent.imageRes,
                        contentDescription = imageDescription,
                        modifier = Modifier
                            .width(140.dp)
                            .height(140.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = dialogContent.title,
                    style = HankkiTheme.typography.sub1,
                    color = Gray900,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = dialogContent.subTitle,
                    style = HankkiTheme.typography.body1,
                    color = Gray500,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(36.dp))

                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "돌아가기",
                        modifier = Modifier
                            .padding(vertical = 22.dp, horizontal = 15.dp)
                            .noRippleClickable(onClick = onConfirmation)
                            .clip(RoundedCornerShape(14.dp)),
                        style = HankkiTheme.typography.sub3,
                        textAlign = TextAlign.Center,
                        color = Red
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onDismissRequest() },
                        colors = ButtonDefaults.buttonColors(containerColor = Red),
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(
                            vertical = 15.dp,
                            horizontal = 22.dp
                        ) //왜 lineheight이 반영되지 않는지에 대해..
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

data class DialogContent(
    val imageRes: Painter,
    val title: String,
    val subTitle: String,
    val buttonTitle: String
)

@Composable
fun getDialogContent(imageDescription: String): DialogContent {
    return when (imageDescription) {
        "로그아웃" -> DialogContent(
            imageRes = painterResource(id = R.drawable.ic_launcher_background),
            title = "정말 로그아웃 하실 건가요?",
            subTitle = "kakao 계정을 로그아웃합니다",
            buttonTitle = "로그아웃"
        )

        else -> DialogContent(
            imageRes = painterResource(id = R.drawable.ic_launcher_foreground),
            title = "소중한 족보가 사라져요",
            subTitle = "탈퇴 계정은 복구할 수 없어요",
            buttonTitle = "탈퇴하기"
        )
    }
}

@Preview
@Composable
fun DialogPrev() {
    HankkijogboTheme {
        DialogWithImage(
            onDismissRequest = { },
            onConfirmation = { },
            imageDescription = ""
        )
    }
}
