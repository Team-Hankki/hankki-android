package com.hankki.feature.home.model

import androidx.compose.ui.graphics.Color
import com.hankki.core.designsystem.theme.Gray300
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray500
import com.hankki.core.designsystem.theme.Gray600
import com.hankki.core.designsystem.theme.White
import com.hankki.core.designsystem.theme.Yellow
import com.hankki.core.designsystem.theme.YellowLight

sealed class ChipState {
    abstract val title: String
    abstract val tag: String
    abstract val style: ChipStyle

    data class Selected(override val title: String = "", override val tag: String = "") : ChipState() {
        override val style: ChipStyle = ChipStyle.SELECTED
    }

    data class Unselected(override val title: String = "", override val tag: String = "") : ChipState() {
        override val style: ChipStyle = ChipStyle.UNSELECTED
    }

    data class Fixed(override val title: String, override val tag: String) : ChipState() {
        override val style: ChipStyle = ChipStyle.FIXED
    }
}

data class ChipStyle(
    val containerColor: Color,
    val borderColor: Color,
    val labelColor: Color,
    val iconColor: Color
) {
    companion object {
        val SELECTED = ChipStyle(
            containerColor = White,
            borderColor = Gray300,
            labelColor = Gray600,
            iconColor = Gray600
        )

        val UNSELECTED = ChipStyle(
            containerColor = White,
            borderColor = Gray300,
            labelColor = Gray500,
            iconColor = Gray500
        )

        val FIXED = ChipStyle(
            containerColor = YellowLight,
            borderColor = Yellow,
            labelColor = Gray600,
            iconColor = Gray600
        )
    }
}
