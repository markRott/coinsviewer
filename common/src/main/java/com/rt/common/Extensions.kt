package com.rt.common

fun Double.coinPriceFormat(digits: Int = 2) = "%.${digits}f".format(this)
    .replace(",", ".")

fun String.coinPriceFormat(digits: Int = 2): String {
    val toDouble = this.toDoubleOrNull() ?: 0.0
    return "%.${digits}f".format(toDouble).replace(",", ".")
}