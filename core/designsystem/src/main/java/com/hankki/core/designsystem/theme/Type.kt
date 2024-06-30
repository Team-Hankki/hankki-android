package com.hankki.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hankki.core.designsystem.R

// 예시 파일입니다. 이렇게 사용하시면 되고, 아래에 body1에 적용한 예시까지 있습니다.
// 아무 폰트나 넣은거라 폰트가 맞는지 확인 후 사용해주세요.
// 적용 하신 후 주석 제거해주세요.
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))

@Stable
class HankkiTypography internal constructor(
    h1: TextStyle,
    h2: TextStyle,
    suit_h1: TextStyle,
    sub1: TextStyle,
    sub2: TextStyle,
    sub3: TextStyle,
    suit_s1: TextStyle,
    body1: TextStyle,
    body2: TextStyle,
    body3: TextStyle,
    body4: TextStyle,
    suit_body1: TextStyle,
    suit_body2: TextStyle,
    button1: TextStyle,
    caption1: TextStyle,
    catption2: TextStyle,
) {
    var h1: TextStyle by mutableStateOf(h1)
        private set
    var h2: TextStyle by mutableStateOf(h2)
        private set
    var suit_h1: TextStyle by mutableStateOf(suit_h1)
        private set
    var sub1: TextStyle by mutableStateOf(sub1)
        private set
    var sub2: TextStyle by mutableStateOf(sub2)
        private set
    var sub3: TextStyle by mutableStateOf(sub3)
        private set
    var suit_s1: TextStyle by mutableStateOf(suit_s1)
        private set
    var body1: TextStyle by mutableStateOf(body1)
        private set
    var body3: TextStyle by mutableStateOf(body3)
        private set
    var body2: TextStyle by mutableStateOf(body2)
        private set
    var body4: TextStyle by mutableStateOf(body4)
        private set
    var suit_body1: TextStyle by mutableStateOf(suit_body1)
        private set
    var suit_body2: TextStyle by mutableStateOf(suit_body2)
        private set
    var button1: TextStyle by mutableStateOf(button1)
        private set
    var caption1: TextStyle by mutableStateOf(caption1)
        private set
    var caption2: TextStyle by mutableStateOf(catption2)
        private set

    fun copy(
        h1: TextStyle = this.h1,
        h2: TextStyle = this.h2,
        suit_h1: TextStyle = this.suit_h1,
        sub1: TextStyle = this.sub1,
        sub2: TextStyle = this.sub2,
        sub3: TextStyle = this.sub3,
        suit_s1: TextStyle = this.suit_s1,
        body1: TextStyle = this.body1,
        body2: TextStyle = this.body2,
        body3: TextStyle = this.body3,
        body4: TextStyle = this.body4,
        suit_body1: TextStyle = this.suit_body1,
        suit_body2: TextStyle = this.suit_body2,
        button1: TextStyle = this.button1,
        caption1: TextStyle = this.caption1,
        caption2: TextStyle = this.caption2,
    ): HankkiTypography = HankkiTypography(
        h1,
        h2,
        suit_h1,
        sub1,
        sub2,
        sub3,
        suit_s1,
        body1,
        body2,
        body3,
        body4,
        suit_body1,
        suit_body2,
        button1,
        caption1,
        caption2
    )

    fun update(other: HankkiTypography) {
        h1 = other.h1
        h2 = other.h2
        suit_h1 = other.suit_h1
        sub1 = other.sub1
        sub2 = other.sub2
        sub3 = other.sub3
        suit_s1 = other.suit_s1
        body1 = other.body1
        body2 = other.body2
        body3 = other.body3
        body4 = other.body4
        suit_body1 = other.suit_body1
        suit_body2 = other.suit_body2
        button1 = other.button1
        caption1 = other.caption1
        caption2 = other.caption2
    }
}

@Composable
fun hankkiTypography(): HankkiTypography {
    return HankkiTypography(
        h1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        ),
        h2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        suit_h1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        sub1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        sub2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        ),
        sub3 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        ),
        suit_s1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        ),
        body1 = TextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        body2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        body3 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        body4 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        ),
        suit_body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp
        ),
        suit_body2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 6.sp
        ),
        button1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        ),
        caption1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        catption2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        )
    )
}
