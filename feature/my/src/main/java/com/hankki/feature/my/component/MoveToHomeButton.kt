package com.hankki.feature.my.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hankki.core.common.extension.noRippleClickable
import com.hankki.core.designsystem.theme.Gray100
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.HankkiTheme
import com.hankki.feature.my.R

@Composable
fun MoveToHomeButton(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {} // 컴폰넌트가 이 변수를 가져도 되는가에 대한 의문... sideEffect 사용?
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .wrapContentSize()
            .background(Gray100)
            .padding(vertical = 10.dp, horizontal = 12.dp)
            .padding(end = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = "add",
            modifier = Modifier.size(24.dp),
            tint = Gray500
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = stringResource(R.string.go_to_store),
            color = Gray500,
            style = HankkiTheme.typography.body6,
            modifier = Modifier.noRippleClickable(navigateToHome)
        )
    }
}