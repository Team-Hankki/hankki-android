package com.hankki.core.common.extension

import java.text.DecimalFormat

fun String.formatPrice(): String {
    return try {
        val priceValue = this.toInt()
        val formatter = DecimalFormat("#,###")
        formatter.format(priceValue)
    } catch (e: NumberFormatException) {
        this
    }
}
