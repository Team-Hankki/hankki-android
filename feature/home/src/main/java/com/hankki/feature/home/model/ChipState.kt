package com.hankki.feature.home.model

import androidx.compose.ui.graphics.Color
import com.hankki.core.designsystem.theme.Gray400
import com.hankki.core.designsystem.theme.Gray800
import com.hankki.core.designsystem.theme.Red100
import com.hankki.core.designsystem.theme.Red500
import com.hankki.core.designsystem.theme.White

sealed class ChipState {
    abstract val title: String
    abstract val tag: String
    abstract val style: ChipStyle

    data class Selected(override val title: String = "", override val tag: String = "") :
        ChipState() {
        override val style: ChipStyle = ChipStyle.SELECTED
    }

    data class Unselected(override val title: String = "", override val tag: String = "") :
        ChipState() {
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
    val iconColor: Color,
) {
    companion object {
        val SELECTED = ChipStyle(
            containerColor = White,
            borderColor = Color.Transparent,
            labelColor = Gray800,
            iconColor = Gray400
        )

        val UNSELECTED = ChipStyle(
            containerColor = White,
            borderColor = Color.Transparent,
            labelColor = Gray800,
            iconColor = Gray400
        )

        val FIXED = ChipStyle(
            containerColor = Red100,
            borderColor = Red500,
            labelColor = Red500,
            iconColor = Red500
        )
    }
}

enum class HomeChips() {
    CATEGORY,
    PRICE,
    SORT,
}
