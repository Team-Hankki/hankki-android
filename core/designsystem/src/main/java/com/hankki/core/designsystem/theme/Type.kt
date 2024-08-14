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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.hankki.core.designsystem.R

val PretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal))
val SuiteBold = FontFamily(Font(R.font.suite_bold, FontWeight.Bold))
val SuiteSemiBold = FontFamily(Font(R.font.suite_semibold, FontWeight.SemiBold))
val SuiteMedium = FontFamily(Font(R.font.suite_medium, FontWeight.Medium))
val SuiteRegular = FontFamily(Font(R.font.suite_regular, FontWeight.Normal))

@Stable
class HankkiTypography internal constructor(
    h1: TextStyle,
    h2: TextStyle,
    suitH1: TextStyle,
    suitH2: TextStyle,
    suitH3: TextStyle,
    sub1: TextStyle,
    sub2: TextStyle,
    sub3: TextStyle,
    suitSub1: TextStyle,
    body1: TextStyle,
    body3: TextStyle,
    body4: TextStyle,
    body5: TextStyle,
    body6: TextStyle,
    body2: TextStyle,
    suitBody1: TextStyle,
    suitBody2: TextStyle,
    button1: TextStyle,
    caption1: TextStyle,
    caption2: TextStyle,
) {
    var h1: TextStyle by mutableStateOf(h1)
        private set
    var h2: TextStyle by mutableStateOf(h2)
        private set
    var suitH1: TextStyle by mutableStateOf(suitH1)
        private set
    var suitH2: TextStyle by mutableStateOf(suitH2)
        private set
    var suitH3: TextStyle by mutableStateOf(suitH3)
        private set
    var sub1: TextStyle by mutableStateOf(sub1)
        private set
    var sub2: TextStyle by mutableStateOf(sub2)
        private set
    var sub3: TextStyle by mutableStateOf(sub3)
        private set
    var suitSub1: TextStyle by mutableStateOf(suitSub1)
        private set
    var body1: TextStyle by mutableStateOf(body1)
        private set
    var body2: TextStyle by mutableStateOf(body2)
        private set
    var body3: TextStyle by mutableStateOf(body3)
        private set
    var body4: TextStyle by mutableStateOf(body4)
        private set
    var body5: TextStyle by mutableStateOf(body5)
        private set
    var body6: TextStyle by mutableStateOf(body6)
        private set
    var suitBody1: TextStyle by mutableStateOf(suitBody1)
        private set
    var suitBody2: TextStyle by mutableStateOf(suitBody2)
        private set
    var button1: TextStyle by mutableStateOf(button1)
        private set
    var caption1: TextStyle by mutableStateOf(caption1)
        private set
    var caption2: TextStyle by mutableStateOf(caption2)
        private set

    fun copy(
        h1: TextStyle = this.h1,
        h2: TextStyle = this.h2,
        suitH1: TextStyle = this.suitH1,
        suitH2: TextStyle = this.suitH2,
        suitH3: TextStyle = this.suitH3,
        sub1: TextStyle = this.sub1,
        sub2: TextStyle = this.sub2,
        sub3: TextStyle = this.sub3,
        suitSub1: TextStyle = this.suitSub1,
        body1: TextStyle = this.body1,
        body2: TextStyle = this.body2,
        body3: TextStyle = this.body3,
        body4: TextStyle = this.body4,
        body5: TextStyle = this.body5,
        body6: TextStyle = this.body6,
        suitBody1: TextStyle = this.suitBody1,
        suitBody2: TextStyle = this.suitBody2,
        button1: TextStyle = this.button1,
        caption1: TextStyle = this.caption1,
        caption2: TextStyle = this.caption2,
    ): HankkiTypography = HankkiTypography(
        h1,
        h2,
        suitH1,
        suitH2,
        suitH3,
        sub1,
        sub2,
        sub3,
        suitSub1,
        body1,
        body2,
        body3,
        body4,
        body5,
        body6,
        suitBody1,
        suitBody2,
        button1,
        caption1,
        caption2
    )

    fun update(other: HankkiTypography) {
        h1 = other.h1
        h2 = other.h2
        suitH1 = other.suitH1
        suitH2 = other.suitH2
        suitH3 = other.suitH3
        sub1 = other.sub1
        sub2 = other.sub2
        sub3 = other.sub3
        suitSub1 = other.suitSub1
        body1 = other.body1
        body2 = other.body2
        body3 = other.body3
        body4 = other.body4
        body5 = other.body5
        body6 = other.body6
        suitBody1 = other.suitBody1
        suitBody2 = other.suitBody2
        button1 = other.button1
        caption1 = other.caption1
        caption2 = other.caption2
    }
}

fun hankkiTextStyle(
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontWeight = fontWeight,
    fontSize = fontSize,
    lineHeight = lineHeight,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
)

@Composable
fun hankkiTypography(): HankkiTypography {
    return HankkiTypography(
        h1 = hankkiTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 36.sp
        ),
        h2 = hankkiTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 30.sp
        ),
        suitH1 = hankkiTextStyle(
            fontFamily = SuiteBold,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 36.sp
        ),
        suitH2 = hankkiTextStyle(
            fontFamily = SuiteBold,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 30.sp
        ),
        suitH3 = hankkiTextStyle(
            fontFamily = SuiteBold,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 27.sp
        ),
        sub1 = hankkiTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 27.sp
        ),
        sub2 = hankkiTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            lineHeight = 25.5.sp
        ),
        sub3 = hankkiTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        suitSub1 = hankkiTextStyle(
            fontFamily = SuiteSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        body1 = hankkiTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        body2 = hankkiTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            lineHeight = 22.5.sp
        ),
        body3 = hankkiTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 21.sp
        ),
        body4 = hankkiTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 21.sp
        ),
        body5 = hankkiTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 21.sp
        ),
        body6 = hankkiTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            lineHeight = 19.5.sp
        ),
        suitBody1 = hankkiTextStyle(
            fontFamily = SuiteMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        suitBody2 = hankkiTextStyle(
            fontFamily = SuiteRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 21.sp
        ),
        button1 = hankkiTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 18.sp
        ),
        caption1 = hankkiTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 18.sp
        ),
        caption2 = hankkiTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 11.sp,
            lineHeight = 16.5.sp
        )
    )
}
