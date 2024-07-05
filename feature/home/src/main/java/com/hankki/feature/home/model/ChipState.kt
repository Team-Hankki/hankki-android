package com.hankki.feature.home.model

import androidx.compose.ui.graphics.Color
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.SubColor01
import com.hankki.core.designsystem.theme.SubColor02
import com.hankki.core.designsystem.theme.White

enum class ChipState(
    val containerColor: Color,
    val borderColor: Color,
    val labelColor: Color,
    val iconColor: Color,
    var title: String = "",
) {
    SELECTED(
        containerColor = White,
        borderColor = Gray300,
        labelColor = Gray600,
        iconColor = Gray600
    ),
    UNSELECTED(
        containerColor = White,
        borderColor = Gray300,
        labelColor = Gray400,
        iconColor = Gray400
    ),
    FIXED(
        containerColor = SubColor01,
        borderColor = SubColor02,
        labelColor = Gray600,
        iconColor = Gray600
    )
}
