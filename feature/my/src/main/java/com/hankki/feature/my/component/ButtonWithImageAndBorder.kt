package com.hankki.feature.my.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hankki.core.designsystem.theme.Gray200
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray50
import com.hankki.core.designsystem.theme.Gray900
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.core.designsystem.theme.HankkijogboTheme
import com.hankki.feature.my.R

@Composable
fun ButtonWithImageAndBorder(
    image: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(
                Gray50,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .border(
                1.dp,
                color = Gray200,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .padding(vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = image),
            contentDescription = description,
            tint = Gray400
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = description,
            style = HankkiTheme.typography.body3,
            color = Gray900,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun ButtonPrev() {
    HankkijogboTheme {
        ButtonWithImageAndBorder(
            image = R.drawable.ic_good,
            description = "test",
            modifier = Modifier
        )
    }
}
