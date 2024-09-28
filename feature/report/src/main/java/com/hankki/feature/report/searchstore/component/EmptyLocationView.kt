package com.hankki.feature.report.searchstore.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.report.R

@Composable
fun EmptyLocationView(
    text: String,
    modifier: Modifier = Modifier,
) {
    val baseText = "에 대한\n검색 결과가 없어요"

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = HankkiTheme.typography.body3.toSpanStyle().copy(
                color = Gray800
            )
        ) {
            append("'$text'")
        }
        withStyle(
            style = HankkiTheme.typography.body3.toSpanStyle().copy(
                color = Gray500
            )
        ) {
            append(baseText)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.img_empty),
                contentDescription = "empty",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = annotatedString,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyLocationViewPreview() {
    HankkijogboTheme {
        EmptyLocationView(
            text = "한끼네 한정식"
        )
    }
}
