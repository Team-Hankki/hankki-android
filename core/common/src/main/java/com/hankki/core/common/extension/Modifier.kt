package com.hankki.core.common.extension

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit = {},
): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

fun Modifier.addFocusCleaner(focusManager: FocusManager): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
        })
    }
}

@Composable
fun Modifier.bounceClick(
    scaleDown: Float = 0.96f,
    blackAlpha: Float = 0.08f,
    radius: Float = 10f,
    onClick: () -> Unit,
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cornerRadius = (1 - scaleDown) * 100 * 0.6f * radius

    val scale by animateFloatAsState(
        if (isPressed) scaleDown else 1f, label = ""
    )

    val color by animateColorAsState(
        if (isPressed) Color.Black.copy(alpha = blackAlpha) else Color.Transparent, label = ""
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
        .drawWithContent {
            drawContent()
            if (isPressed) {
                drawRoundRect(
                    cornerRadius = CornerRadius(cornerRadius),
                    color = color,
                    size = size,
                    blendMode = BlendMode.SrcAtop
                )
            }
        }
}

fun Modifier.dashedBorder(strokeWidth: Dp, color: Color, cornerRadiusDp: Dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val cornerRadiusPx = density.run { cornerRadiusDp.toPx() }

        this.then(
            Modifier.drawWithCache {
                onDrawBehind {
                    val stroke = Stroke(
                        width = strokeWidthPx,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )

                    drawRoundRect(
                        color = color,
                        style = stroke,
                        cornerRadius = CornerRadius(cornerRadiusPx)
                    )
                }
            }
        )
    }
)

fun Modifier.ignoreNextModifiers(): Modifier {
    return object : Modifier by this {

        override fun then(other: Modifier): Modifier {
            return this
        }
    }
}
